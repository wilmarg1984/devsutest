package com.devsu.customerservice.service;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.devsu.common.domain.Customer;
import com.devsu.common.domain.Parameter;
import com.devsu.common.domain.Person;
import com.devsu.common.exceptions.BusinessException;
import com.devsu.common.exceptions.DataBaseException;
import com.devsu.customerservice.mapper.CustomerMapper;
import com.devsu.customerservice.model.CustomerDTO;
import com.devsu.customerservice.repository.CustomerRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ServiceCustomer {

    private final CustomerRepository customerRepository;
    private final ServicePerson servicePerson;
    private final CustomerMapper customerMapper;

    public ResponseEntity<?> getCustomers() {
        try {
            List<Customer> customers = customerRepository.getCustomers();
            List<CustomerDTO> customersDTO = customers.stream()
                .map(customerMapper::toDto)
                .toList();
            return new ResponseEntity<>(customersDTO, HttpStatus.OK);
        } catch (Exception e) {
            throw new DataBaseException("Error al obtener los clientes", e);
        }
    }

    @Transactional(readOnly = true)
    public ResponseEntity<?> getCustomerById(Long id) {
        try {
            Optional<Customer> customerOpt = customerRepository.findById(id);
            if (!customerOpt.isPresent()) {
                throw new BusinessException("Cliente no encontrado con ID: " + id);
            }
            CustomerDTO customerDTO = customerMapper.toDto(customerOpt.get());
            return new ResponseEntity<>(customerDTO, HttpStatus.OK);
        } catch (BusinessException e) {
            throw e;
        } catch (Exception e) {
            throw new DataBaseException("Error al obtener el cliente", e);
        }
    }

    @Transactional
    public ResponseEntity<?> createCustomer(CustomerDTO dto) {
        try {
            // Validar que el cliente tenga los datos necesarios
            if (dto.getPerson() == null) {
                throw new BusinessException("Los datos de la persona son requeridos");
            }
            
            // Crear la persona usando el servicio especializado
            Person person = servicePerson.createPerson(dto.getPerson());
            
            // Crear y guardar el cliente
            Customer customer = customerMapper.toEntity(dto);
            customer.setIdPerson(person);
            customer = customerRepository.save(customer);
            
            // Crear response con el ID del cliente creado
            Map<String, Object> response = new HashMap<>();
            response.put("idCustomer", customer.getIdCustomer());
            
            return new ResponseEntity<>(response, HttpStatus.CREATED);
            
        } catch (BusinessException e) {
            throw e;
        } catch (Exception e) {
            throw new DataBaseException("Error al crear el cliente", e);
        }
    }

    @Transactional
    public ResponseEntity<?> updateCustomer(Long id, CustomerDTO dto) {
        try {
            Optional<Customer> customerOpt = customerRepository.findById(id);
            if (!customerOpt.isPresent()) {
                throw new BusinessException("Cliente no encontrado con ID: " + id);
            }

            Customer existingCustomer = customerOpt.get();
            
            // Actualizar la persona asociada
            if (dto.getPerson() != null) {
                Person updatedPerson = servicePerson.updatePerson(
                    existingCustomer.getIdPerson().getIdPerson(), 
                    dto.getPerson()
                );
                existingCustomer.setIdPerson(updatedPerson);
            }
            
            // Actualizar datos del cliente
            existingCustomer.setPassword(dto.getPassword());
            if (dto.getIdStatus() != null) {
                Parameter statusParam = new Parameter();
                statusParam.setIdParameter(dto.getIdStatus());
                existingCustomer.setIdStatus(statusParam);
            }
            
            existingCustomer = customerRepository.save(existingCustomer);
            CustomerDTO updatedDTO = customerMapper.toDto(existingCustomer);
            
            return new ResponseEntity<>(updatedDTO, HttpStatus.OK);
            
        } catch (BusinessException e) {
            throw e;
        } catch (Exception e) {
            throw new DataBaseException("Error al actualizar el cliente", e);
        }
    }

    @Transactional
    public ResponseEntity<?> deleteCustomer(Long id) {
        try {
            Optional<Customer> customerOpt = customerRepository.findById(id);
            if (!customerOpt.isPresent()) {
                throw new BusinessException("Cliente no encontrado con ID: " + id);
            }
            
            Customer customer = customerOpt.get();
            customerRepository.delete(customer);
            
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            
        } catch (BusinessException e) {
            throw e;
        } catch (Exception e) {
            throw new DataBaseException("Error al eliminar el cliente", e);
        }
    }
}
