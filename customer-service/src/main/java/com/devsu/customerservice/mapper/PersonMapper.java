package com.devsu.customerservice.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.devsu.common.domain.Parameter;
import com.devsu.common.domain.Person;
import com.devsu.customerservice.model.PersonDTO;

@Mapper(componentModel = "spring", uses = {})
public interface PersonMapper {
    
    @Mapping(target = "idTypeIDNumber.idParameter", source = "idTypeIDNumber")
    @Mapping(target = "idGender.idParameter", source = "idGender")
    @Mapping(target = "idStatus.idParameter", source = "idStatus")
    @Mapping(target = "name", source = "name")
    @Mapping(target = "address", source = "address")
    @Mapping(target = "phone", source = "phone")
    @Mapping(target = "email", source = "email")
    @Mapping(target = "identificationNumber", source = "identificationNumber")
    Person toEntity(PersonDTO dto);

    @Mapping(target = "idTypeIDNumber", source = "idTypeIDNumber.idParameter")
    @Mapping(target = "idGender", source = "idGender.idParameter")
    @Mapping(target = "idStatus", source = "idStatus.idParameter")
    @Mapping(target = "name", source = "name")
    @Mapping(target = "address", source = "address")
    @Mapping(target = "phone", source = "phone")
    @Mapping(target = "email", source = "email")
    @Mapping(target = "identificationNumber", source = "identificationNumber")
    PersonDTO toDto(Person persona);

    default Parameter map(Long value) {
        if (value == null) {
            return null;
        }
        Parameter parameter = new Parameter();
        parameter.setIdParameter(value);
        return parameter;
    }

    default Long map(Parameter parameter) {
        return parameter != null ? parameter.getIdParameter() : null;
    }
}
