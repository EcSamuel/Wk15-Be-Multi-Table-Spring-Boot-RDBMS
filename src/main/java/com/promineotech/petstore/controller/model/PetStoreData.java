package com.promineotech.petstore.controller.model;

import com.promineotech.petstore.entity.PetStore;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.promineotech.petstore.entity.Customer;
import com.promineotech.petstore.entity.Employee;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
public class PetStoreData {
    private Long petStoreId;
    private String storeName;
    private String storeAddress;
    private String storePhone;
    private String storeCity;
    private String storeState;
    private String storeZip;
    private Set<PetStoreCustomer> customers = new HashSet<>();
    private Set<PetStoreEmployee> employees = new HashSet<>();

    public PetStoreData(PetStore petStore) {
        this.petStoreId = petStore.getPetStoreId();
        this.storeName = petStore.getStoreName();
        this.storeAddress = petStore.getStoreAddress();
        this.storePhone = petStore.getStorePhone();
        this.storeCity = petStore.getStoreCity();
        this.storeState = petStore.getStoreState();
        this.storeZip = petStore.getStoreZip();
//        this.customers = petStore.getCustomers().stream()
//                .map(PetStoreCustomer::new)
//                .collect(Collectors.toSet());
//        this.employees = petStore.getEmployees().stream()
//                .map(PetStoreEmployee::new)
//                .collect(Collectors.toSet());
        for (Customer customer : petStore.getCustomers()) {
            customers.add(new PetStoreCustomer(customer));
        }

        for (Employee employee : petStore.getEmployees()) {
            employees.add(new PetStoreEmployee(employee));
        }
    }
}