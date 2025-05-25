package com.devsu.accountservice.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import com.devsu.common.domain.Parameter;
import com.devsu.accountservice.domain.Account;
import com.devsu.accountservice.model.AccountDTO;

@Mapper(componentModel = "spring")
public interface AccountMapper {
    
    @Mapping(target = "accountId", source = "idAccount")
    @Mapping(target = "customerId", source = "customer.idCustomer")
    @Mapping(target = "accountType", source = "accountType.idParameter")
    @Mapping(target = "status", source = "status.idParameter")
    AccountDTO toDto(Account account);
    
    @Mapping(target = "idAccount", source = "accountId")
    @Mapping(target = "customer.idCustomer", source = "customerId")
    @Mapping(target = "accountType.idParameter", source = "accountType")
    @Mapping(target = "status.idParameter", source = "status")
    Account toEntity(AccountDTO dto);
    
    @Mapping(target = "idAccount", source = "accountId")
    @Mapping(target = "customer.idCustomer", source = "customerId")
    @Mapping(target = "accountType.idParameter", source = "accountType")
    @Mapping(target = "status.idParameter", source = "status")
    void updateEntity(@MappingTarget Account account, AccountDTO dto);
    
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