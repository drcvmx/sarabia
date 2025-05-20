package com.vs.customer.dto;

import lombok.Data;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Email;

@Data
public class CustomerDTO {
    @NotBlank(message = "El ID no puede estar vacío")
    private String id;

    @NotBlank(message = "El nombre del cliente no puede estar vacío")
    private String customerName;

    @NotBlank(message = "El email no puede estar vacío")
    @Email(message = "El formato del email no es válido")
    private String email;

    @NotBlank(message = "La contraseña no puede estar vacía")
    private String password;

    private String phoneNumber;
}
