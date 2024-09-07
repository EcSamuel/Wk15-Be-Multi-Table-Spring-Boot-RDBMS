package com.promineotech.petstore.controller.model;

import com.promineotech.petstore.entity.Customer;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PetStoreCustomer {
    private Long customerId;
    private String customerEmail;
    private String customerName;

    public PetStoreCustomer(Customer customer) {
        this.customerId = customer.getId();
        this.customerEmail = customer.getCustomerEmail();
        this.customerName = customer.getCustomerName();
    }
}
