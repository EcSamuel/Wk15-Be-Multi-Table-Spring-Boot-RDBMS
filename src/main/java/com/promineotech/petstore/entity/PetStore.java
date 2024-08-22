package com.promineotech.petstore.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.HashSet;
import java.util.Set;

// Mark this class as a JPA entity
@Entity
@Data // Lombok annotation for getters, setters, toString, etc.
public class PetStore {

    // Define the primary key with auto-generated value
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column// Instance variables reflecting table columns
    private String storeName;
    @Column
    private String storeAddress;

    // Relationship with Customers
    @ManyToMany(cascade = CascadeType.PERSIST)
    @JoinTable(
            name = "pet_store_customer",
            joinColumns = @JoinColumn(name = "pet_store_id"),
            inverseJoinColumns = @JoinColumn(name = "customer_id")
    )
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Set<Customer> customers = new HashSet<>();

    // Relationship with Employees
    @OneToMany(mappedBy = "petStore", cascade = CascadeType.ALL, orphanRemoval = true)
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Set<Employee> employees = new HashSet<>();
}
