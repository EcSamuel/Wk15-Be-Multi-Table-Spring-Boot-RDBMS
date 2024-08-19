package pet.store.controller.model;

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
    private Set<PetStoreCustomer> customers = new HashSet<>();
    private Set<PetStoreEmployee> employees = new HashSet<>();

    public PetStoreData(PetStore petStore) {
        this.petStoreId = petStore.getId();
        this.storeName = petStore.getStoreName();
        this.storeAddress = petStore.getStoreAddress();
        this.customers = petStore.getCustomers().stream()
                .map(PetStoreCustomer::new)
                .collect(Collectors.toSet());
        this.employees = petStore.getEmployees().stream()
                .map(PetStoreEmployee::new)
                .collect(Collectors.toSet());
    }

    @Data
    @NoArgsConstructor
    public static class PetStoreCustomer {
        private Long customerId;
        private String customerEmail;
        private String customerName;

        public PetStoreCustomer(Customer customer) {
            this.customerId = customer.getId();
            this.customerEmail = customer.getCustomerEmail();
            this.customerName = customer.getCustomerName();
        }
    }

    @Data
    @NoArgsConstructor
    public static class PetStoreEmployee {
        private Long employeeId;
        private String employeeName;
        private String employeeRole;

        public PetStoreEmployee(Employee employee) {
            this.employeeId = employee.getId();
            this.employeeName = employee.getEmployeeName();
            this.employeeRole = employee.getEmployeeRole();
        }
    }
}