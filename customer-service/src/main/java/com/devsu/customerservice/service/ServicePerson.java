package com.devsu.customerservice.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.devsu.common.domain.Person;
import com.devsu.common.exceptions.BusinessException;
import com.devsu.common.exceptions.DataBaseException;
import com.devsu.customerservice.mapper.PersonMapper;
import com.devsu.customerservice.model.PersonDTO;
import com.devsu.customerservice.repository.PersonRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ServicePerson {

    private final PersonRepository personRepository;
    private final PersonMapper personMapper;

    @Transactional(readOnly = true)
    public List<Person> getPersons() {
        try {
            return personRepository.getPersons();
        } catch (Exception e) {
            throw new DataBaseException("Error getting persons", e);
        }
    }

    @Transactional(readOnly = true)
    public Person getPersonById(Long id) {
        try {
            Optional<Person> person = personRepository.findById(id);
            if (!person.isPresent()) {
                throw new BusinessException("Person not found with ID: " + id);
            }
            return person.get();
        } catch (BusinessException e) {
            throw e;
        } catch (Exception e) {
            throw new DataBaseException("Error getting person", e);
        }
    }

    @Transactional
    public Person createPerson(PersonDTO personDTO) {
        try {
            validatePersonData(personDTO);
            Person person = personMapper.toEntity(personDTO);
            return personRepository.save(person);
        } catch (BusinessException e) {
            throw e;
        } catch (Exception e) {
            throw new DataBaseException("Error creating person", e);
        }
    }

    @Transactional
    public Person updatePerson(Long id, PersonDTO personDTO) {
        try {
            // Verify person exists
            Person existingPerson = getPersonById(id);
            validatePersonData(personDTO);
            
            // Update data
            Person updatedPerson = personMapper.toEntity(personDTO);
            updatedPerson.setIdPerson(existingPerson.getIdPerson());
            
            return personRepository.save(updatedPerson);
        } catch (BusinessException e) {
            throw e;
        } catch (Exception e) {
            throw new DataBaseException("Error updating person", e);
        }
    }

    private void validatePersonData(PersonDTO personDTO) {
        if (personDTO == null) {
            throw new BusinessException("Person data is required");
        }
        if (personDTO.getName() == null || personDTO.getName().trim().isEmpty()) {
            throw new BusinessException("Name is required");
        }
        if (personDTO.getIdentificationNumber() == null || personDTO.getIdentificationNumber().trim().isEmpty()) {
            throw new BusinessException("Identification number is required");
        }
        // You can add more validations according to your requirements
    }
} 