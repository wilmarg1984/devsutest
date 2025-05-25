package com.devsu.customerservice.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.devsu.common.domain.Customer;
import com.devsu.common.domain.Parameter;
import com.devsu.common.domain.Person;
import com.devsu.common.exceptions.BusinessException;
import com.devsu.customerservice.mapper.CustomerMapper;
import com.devsu.customerservice.model.CustomerDTO;
import com.devsu.customerservice.model.PersonDTO;
import com.devsu.customerservice.repository.CustomerRepository;

@ExtendWith(MockitoExtension.class)
class ServiceCustomerTest {

    @Mock
    private CustomerRepository customerRepository;

    @Mock
    private ServicePerson servicePerson;

    @Mock
    private CustomerMapper customerMapper;

    @InjectMocks
    private ServiceCustomer serviceCustomer;

    private CustomerDTO customerDTO;
    private PersonDTO personDTO;
    private Person person;
    private Customer customer;
    private Parameter statusParameter;

    @BeforeEach
    void setUp() {
        // Configurar datos de prueba
        personDTO = new PersonDTO();
        personDTO.setName("Wilmar Gomez");
        personDTO.setAddress("Calle 10 # 11-23");
        personDTO.setPhone("3114609999");
        personDTO.setEmail("wilmar@prueba.com");
        personDTO.setIdentificationNumber("80145999");
        personDTO.setIdTypeIDNumber(3L);
        personDTO.setIdGender(7L);
        personDTO.setIdStatus(1L);

        customerDTO = new CustomerDTO();
        customerDTO.setPerson(personDTO);
        customerDTO.setPassword("123456789");
        customerDTO.setIdStatus(9L);

        statusParameter = new Parameter();
        statusParameter.setIdParameter(9L);

        person = new Person();
        person.setIdPerson(1L);
        person.setName("Wilmar Gomez");
        person.setAddress("Calle 10 # 11-23");
        person.setPhone("3114609999");
        person.setEmail("wilmar@prueba.com");
        person.setIdentificationNumber("80145999");

        customer = new Customer();
        customer.setIdCustomer(1L);
        customer.setPassword("123456789");
        customer.setIdPerson(person);
        customer.setIdStatus(statusParameter);
    }

    @Test
    @DisplayName("Debería crear un cliente exitosamente")
    void createCustomer_Success() {
        // Arrange
        when(servicePerson.createPerson(any(PersonDTO.class))).thenReturn(person);
        when(customerMapper.toEntity(any(CustomerDTO.class))).thenReturn(customer);
        when(customerRepository.save(any(Customer.class))).thenReturn(customer);

        // Act
        ResponseEntity<?> response = serviceCustomer.createCustomer(customerDTO);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        
        @SuppressWarnings("unchecked")
        Map<String, Object> responseBody = (Map<String, Object>) response.getBody();
        assertNotNull(responseBody);
        assertEquals(1L, responseBody.get("idCustomer"));

        // Verify
        verify(servicePerson).createPerson(any(PersonDTO.class));
        verify(customerMapper).toEntity(any(CustomerDTO.class));
        verify(customerRepository).save(any(Customer.class));
    }

    @Test
    @DisplayName("Debería lanzar BusinessException cuando no se proporcionan datos de persona")
    void createCustomer_WithoutPerson_ThrowsBusinessException() {
        // Arrange
        customerDTO.setPerson(null);

        // Act & Assert
        BusinessException exception = assertThrows(BusinessException.class,
                () -> serviceCustomer.createCustomer(customerDTO));

        assertEquals("Los datos de la persona son requeridos", exception.getMessage());

        // Verify
        verify(servicePerson, never()).createPerson(any(PersonDTO.class));
        verify(customerMapper, never()).toEntity(any(CustomerDTO.class));
        verify(customerRepository, never()).save(any(Customer.class));
    }

    @Test
    @DisplayName("Debería propagar BusinessException desde ServicePerson")
    void createCustomer_PersonServiceThrowsBusinessException_PropagatesException() {
        // Arrange
        String errorMessage = "Error en datos de persona";
        when(servicePerson.createPerson(any(PersonDTO.class)))
                .thenThrow(new BusinessException(errorMessage));

        // Act & Assert
        BusinessException exception = assertThrows(BusinessException.class,
                () -> serviceCustomer.createCustomer(customerDTO));

        assertEquals(errorMessage, exception.getMessage());

        // Verify
        verify(servicePerson).createPerson(any(PersonDTO.class));
        verify(customerMapper, never()).toEntity(any(CustomerDTO.class));
        verify(customerRepository, never()).save(any(Customer.class));
    }
} 