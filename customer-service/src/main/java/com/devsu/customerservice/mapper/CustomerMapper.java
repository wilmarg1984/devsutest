package com.devsu.customerservice.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.devsu.common.domain.Parameter;
import com.devsu.common.domain.Customer;
import com.devsu.customerservice.model.CustomerDTO;

@Mapper(componentModel = "spring", uses = {PersonMapper.class})
public interface CustomerMapper {

    @Mapping(target = "idPerson", source = "person")
    @Mapping(target = "idStatus.idParameter", source = "idStatus")
    Customer toEntity(CustomerDTO dto);

    @Mapping(target = "person", source = "idPerson")
    @Mapping(target = "idStatus", source = "idStatus.idParameter")
    CustomerDTO toDto(Customer customer);

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
