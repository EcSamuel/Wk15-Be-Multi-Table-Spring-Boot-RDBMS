package com.promineotech.petstore.controller.model;

import com.promineotech.petstore.entity.Employee;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PetStoreEmployee {
    private Long employeeId;
    private String employeeName;
    private String employeeRole;

    public PetStoreEmployee(Employee employee) {
        this.employeeId = employee.getId();
        this.employeeName = employee.getEmployeeName();
        this.employeeRole = employee.getEmployeeRole();
    }
}
