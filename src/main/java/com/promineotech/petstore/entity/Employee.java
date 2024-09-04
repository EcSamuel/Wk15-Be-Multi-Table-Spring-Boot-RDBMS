package com.promineotech.petstore.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Entity
@Data// Lombok annotation for getters, setters, toString, etc.
public class Employee {

    // Define the primary key with auto-generated value
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Instance variables reflecting table columns
    @Column(nullable = false)
    private String employeeName;
    @Column
    private String employeeRole;

    // Relationship with PetStore
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "pet_store_id")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private PetStore petStore;
}
