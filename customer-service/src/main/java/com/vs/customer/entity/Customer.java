package com.vs.customer.entity;

import lombok.Data;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Entity
@Table(name = "CUSTOMERS")
@Data
public class Customer {
    @Id
    @Column(name = "ID", length = 50)
    @NotBlank(message = "El ID no puede estar vacío")
    private String id;

    @Column(name = "CUSTOMER_NAME", length = 100, nullable = false)
    @NotBlank(message = "El nombre del cliente no puede estar vacío")
    @Size(min = 2, max = 100, message = "El nombre debe tener entre 2 y 100 caracteres")
    private String customerName;
} 