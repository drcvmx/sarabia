package com.vs.customer.entity;

import lombok.Data;
import javax.persistence.*;
import org.hibernate.annotations.Type;

@Entity
@Table(name = "CUSTOMERS")
@Data
public class Customer {
    
    @Id
    @Column(name = "CUSTOMER_ID", nullable = false, length = 100)
    private String id;

    @Lob
    @Column(name = "CUSTOMER_DETAILS")
    @Type(type = "org.hibernate.type.TextType")
    private String customerDetails;
} 