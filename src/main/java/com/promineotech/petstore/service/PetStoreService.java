package com.promineotech.petstore.service;

import com.promineotech.petstore.dao.EmployeeDao;
import com.promineotech.petstore.dao.PetStoreDao;
import com.promineotech.petstore.entity.Employee;
import com.promineotech.petstore.entity.PetStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.promineotech.petstore.controller.model.PetStoreData;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;

@Service
public class PetStoreService {

    @Autowired
    private PetStoreDao petStoreDao;

    @Autowired
    private EmployeeDao employeeDao;

    public PetStoreData savePetStore(PetStoreData petStoreData) {
        PetStore petStore = findOrCreatePetStore(petStoreData.getPetStoreId());

        copyPetStoreFields(petStore, petStoreData);

        PetStore savedPetStore = petStoreDao.save(petStore);

        return new PetStoreData(savedPetStore);
    }

    private PetStore findOrCreatePetStore(Long petStoreId) {
        if (petStoreId == null) {
            return new PetStore();
        } else {
            return petStoreDao.findById(petStoreId).orElseThrow(() ->
                    new NoSuchElementException("Pet Store with Id " + petStoreId + " cannot be found!"));
        }
    }

    private void copyPetStoreFields(PetStore petStore, PetStoreData petStoreData) {
        petStore.setStoreName(petStoreData.getStoreName());
        petStore.setStoreAddress(petStoreData.getStoreAddress());
        petStore.setStorecity(petStoreData.getPetStoreCity());
        petStore.setPetStoreId(petStoreData.getPetStoreId());
        petStore.setStorePhone(petStoreData.getPetStorePhone());
        petStore.setStoreState(petStoreData.getPetStoreState());
        petStore.setPetStoreZip(petStoreData.getPetStoreZip());
    }

    @Transactional
    public void savePetStoreEmployee(Long petStoreId, PetStoreEmployee petStoreEmployee) {
        PetStore petStore = findPetStoreById(petStoreId);
        Employee employee = findOrCreateEmployee(petStoreEmployee.getEmployeeId(), petStoreId);
//        employeeId = petStoreEmployee.getEmployeeId();
//        employee = findOrCreateEmployee(petStoreId, employeeId);

        copyEmployeeFields(employee, petStoreEmployee);

        Employee savedEmployee = employeeDao.save(employee);

        return new PetStoreData.PetStoreEmployee(savedEmployee, petStore);

//        copyPetStoreFields(employee, petStoreEmployee);
//
//        employee.setPetStore(petStore);
//
//        petStore.getEmployees().add(employee);
//
//        employee = employeeDao.save(employee);
//
//        PetStoreEmployee savedPetStoreEmployee = new PetStoreEmployee();
//        savedPetStoreEmployee.setEmployeeId(employee.getId());
//        savedPetStoreEmployee.setName(employee.getName());
//        savedPetStoreEmployee.setRole(employee.getRole());
//
//        return savedPetStoreEmployee;
    }

    public Employee findEmployeeById(Long petStoreId, Long employeeId) {
        Employee employee = employeeDao.findById(employeeId)
                .orElseThrow(() -> new NoSuchElementException("Employee not found"));

        if (!employee.getPetStore().getId().equals(petStoreId)) {
            throw new IllegalArgumentException("Pet store ID does not match");
        }

        return employee;
    }

    public Employee findOrCreateEmployee(Long employeeId, Long petStoreId) {
        if(employeeId == null) {
            return new Employee();
        } else {
            return findEmployeeById(employeeId, petStoreId);
        }
    }

    public void copyEmployeeFields(Employee employee, PetStoreEmployee petStoreEmployee) {
        employee.setName(petStoreEmployee.getName());
        employee.setEmployeeRole(petStoreEmployee.getRole()); // check the last call against the Entity and Data
    }

}
