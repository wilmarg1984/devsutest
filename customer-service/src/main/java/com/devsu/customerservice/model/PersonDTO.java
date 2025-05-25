package com.devsu.customerservice.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Email;
import lombok.Data;

@Data
public class PersonDTO {
    
    private Long idPerson;

    @NotBlank(message = "Name is required")
    private String name;

    @NotBlank(message = "Address is required")
    private String address;

    @NotBlank(message = "Phone is required")
    private String phone;

    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email format")
    private String email;

    @NotBlank(message = "Identification number is required")
    private String identificationNumber;

    @NotNull(message = "Identification type is required")
    private Long idTypeIDNumber;

    @NotNull(message = "Gender is required")
    private Long idGender;

    private Long idStatus;
}
