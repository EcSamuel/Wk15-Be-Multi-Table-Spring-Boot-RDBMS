package pet.store.controller.model;

import com.promineotech.petstore.entity.Customer;
import com.promineotech.petstore.entity.PetStore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Set;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
public class PetStoreData {
    public static class PetStore {
        private Long id;
        private String storeName;
        private String storeAddress;
        private Set<Customer> customers;
        private Set<Employee> employees;

        public PetStoreData(PetStore petStore) {
            this.id = petStore.getId();
            this.storeName = petStore.getStoreName();
            this.storeAddress = petStore.getStoreAddress();
            this.customers = petStore.getCustomers().stream()
                    .map(PetStoreCustomer::new)
                    .collect(Collectors.toSet());
            this.employees = petStore.getEmployees().stream()
                    .map(PetStoreEmployee::new)
                    .collect(Collectors.toSet());
        }
    }

    public static class Customer {
        private Long id;

        private String customerEmail;
        private String customerName;

        private Set<com.promineotech.petstore.entity.PetStore> petStores;

        public PetStoreCustomer(Customer customer) {
            this.id = customer.getId();
            this.customerEmail = customer.getCustomerEmail();
            this.customerName = customer.getCustomerName();
        }
    }

    public static class Employee {
        private Long id;

        private String employeeName;
        private String employeeRole;

        private com.promineotech.petstore.entity.PetStore petStore;

        public PetStoreEmployee(Employee employee) {
            this.id = employee.getId();
            this.employeeName = employee.getEmployeeName();
            this.employeeRole = employee.getEmployeeRole();
        }
    }
}
