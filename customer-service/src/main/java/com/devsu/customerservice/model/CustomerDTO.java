package com.devsu.customerservice.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CustomerDTO {    
    
    private Long idCustomer;
    @NotNull(message = "The person is required")
    private PersonDTO person;
    @NotBlank(message = "The password is required")
    private String password;
    //@NotBlank(message = "The status is requiered")
    private Long idStatus;
    
    

}
