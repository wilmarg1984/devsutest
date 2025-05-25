package com.devsu.accountservice.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.devsu.common.domain.Parameter;
import com.devsu.accountservice.domain.Transaction;
import com.devsu.accountservice.model.TransactionDTO;

@Mapper(componentModel = "spring")
public interface TransactionMapper {
    
    @Mapping(target = "accountId", source = "account.idAccount")
    @Mapping(target = "transactionId", source = "idTransaction")
    @Mapping(target = "transactionType", source = "idTypeTransaction.idParameter")
    TransactionDTO toDto(Transaction transaction);
    
    @Mapping(target = "account.idAccount", source = "accountId")
    @Mapping(target = "idTransaction", source = "transactionId")
    @Mapping(target = "idTypeTransaction.idParameter", source = "transactionType")
    Transaction toEntity(TransactionDTO dto);
    
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