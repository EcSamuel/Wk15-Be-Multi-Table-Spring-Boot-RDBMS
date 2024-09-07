package com.promineotech.petstore.service;

import com.promineotech.petstore.controller.model.PetStoreCustomer;
import com.promineotech.petstore.dao.CustomerDao;
import com.promineotech.petstore.dao.EmployeeDao;
import com.promineotech.petstore.dao.PetStoreDao;
import com.promineotech.petstore.entity.Customer;
import com.promineotech.petstore.entity.Employee;
import com.promineotech.petstore.entity.PetStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.promineotech.petstore.controller.model.PetStoreData;
import com.promineotech.petstore.controller.model.PetStoreEmployee;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
public class PetStoreService {

    @Autowired
    private PetStoreDao petStoreDao;

    @Autowired
    private EmployeeDao employeeDao;

    @Autowired
    private CustomerDao customerDao;

    public PetStoreData savePetStore(PetStoreData petStoreData) {
        PetStore petStore = convertToPetStore(petStoreData);
        PetStore savedPetStore = petStoreDao.save(petStore);
        return convertToPetStoreData(savedPetStore);
    }

    private PetStore findOrCreatePetStore(Long petStoreId) {
        if (petStoreId == null) {
            return new PetStore();
        } else {
            return petStoreDao.findById(petStoreId)
                    .orElseThrow(() -> new NoSuchElementException("Pet Store with Id " + petStoreId + " cannot be found!"));
        }
    }

    private void copyPetStoreFields(PetStore petStore, PetStoreData petStoreData) {
        petStore.setStoreName(petStoreData.getStoreName());
        petStore.setStoreAddress(petStoreData.getStoreAddress());
        petStore.setStoreCity(petStoreData.getStoreCity());
        petStore.setPetStoreId(petStoreData.getPetStoreId());
        petStore.setStorePhone(petStoreData.getStorePhone());
        petStore.setStoreState(petStoreData.getStoreState());
        petStore.setStoreZip(petStoreData.getStoreZip());
    }

    private void copyCustomerFields(Customer customer, PetStoreCustomer petStoreCustomer) {
        customer.setCustomerName(customer.getCustomerName());
        customer.setCustomerEmail(customer.getCustomerEmail());
    }

    @Transactional
    public PetStoreEmployee savePetStoreEmployee(Long petStoreId, PetStoreEmployee petStoreEmployee) {
        PetStore petStore = findPetStoreEntityById(petStoreId);
        Employee employee = findOrCreateEmployee(petStoreEmployee.getEmployeeId(), petStoreId);

        copyEmployeeFields(employee, petStoreEmployee);
        employee.setPetStore(petStore);

        Employee savedEmployee = employeeDao.save(employee);

        petStore.getEmployees().add(employee);
        petStoreDao.save(petStore);

        return new PetStoreEmployee(savedEmployee);
    }

    @Transactional
    public void deletePetStoreById(Long petStoreId) {
        PetStore petStore = findOrCreatePetStore(petStoreId);
        // ... rest of the method remains the same
    }

    @Transactional
    public PetStoreCustomer savePetStoreCustomer(Long petStoreId, PetStoreCustomer petStoreCustomer) {
        PetStore petStore = findPetStoreEntityById(petStoreId);
        Customer customer = findOrCreateCustomer(petStoreCustomer.getCustomerId(), petStoreId);

        copyCustomerFields(customer, petStoreCustomer);

        Customer savedCustomer = customerDao.save(customer);

        petStore.getCustomers().add(customer);
        petStoreDao.save(petStore);

        return new PetStoreCustomer(savedCustomer);
    }

    public PetStoreData findPetStoreById(Long petStoreId) {
        PetStore petStore = findPetStoreEntityById(petStoreId);
        return new PetStoreData(petStore);
    }

    private PetStore findPetStoreEntityById(Long petStoreId) {
        return petStoreDao.findById(petStoreId)
                .orElseThrow(() -> new NoSuchElementException("Pet Store with Id " + petStoreId + " cannot be found!"));
    }

    public Customer findCustomerById(Long customerId, Long petStoreId) {
        Customer customer = customerDao.findById(customerId)
                .orElseThrow(() -> new NoSuchElementException("Customer not found"));

        PetStore petStore = petStoreDao.findById(petStoreId)
                .orElseThrow(() -> new NoSuchElementException("Pet store not found"));

        if (!customer.getPetStores().contains(petStore)) {
            throw new IllegalArgumentException("Pet store not associated with this customer");
        }

        return customer;
    }

    public Employee findEmployeeById(Long petStoreId, Long employeeId) {
        Employee employee = employeeDao.findById(employeeId)
                .orElseThrow(() -> new NoSuchElementException("Employee not found"));

        if (!employee.getPetStore().getPetStoreId().equals(petStoreId)) {
            throw new IllegalArgumentException("Pet store ID does not match");
        }

        return employee;
    }

    private Customer findOrCreateCustomer(Long customerId, Long petStoreId) {
        if (customerId == null) {
            return new Customer();
        } else {
            return findCustomerById(customerId, petStoreId);
        }
    }

    public Employee findOrCreateEmployee(Long employeeId, Long petStoreId) {
        if(employeeId == null) {
            return new Employee();
        } else {
            return findEmployeeById(employeeId, petStoreId);
        }
    }

    public void copyEmployeeFields(Employee employee, PetStoreEmployee petStoreEmployee) {
        employee.setEmployeeName(petStoreEmployee.getEmployeeName());
        employee.setEmployeeRole(petStoreEmployee.getEmployeeRole()); // check the last call against the Entity and Data
    }

    public List<PetStoreData> getAllPetStores() {
        List<PetStore> petStores = petStoreDao.findAll();
        return petStores.stream()
                .map(this::convertToPetStoreData)
                .collect(Collectors.toList());
    }

    private PetStoreData convertToPetStoreData(PetStore petStore) {
        PetStoreData petStoreData = new PetStoreData();
        petStoreData.setPetStoreId(petStore.getPetStoreId());
        petStoreData.setStoreName(petStore.getStoreName());
        petStoreData.setStoreAddress(petStore.getStoreAddress());
        petStoreData.setStoreCity(petStore.getStoreCity());
        petStoreData.setStoreState(petStore.getStoreState());
        petStoreData.setStoreZip(petStore.getStoreZip());
        petStoreData.setStorePhone(petStore.getStorePhone());
        return petStoreData;
    }

    private PetStore convertToPetStore(PetStoreData petStoreData) {
        PetStore petStore = new PetStore();
        petStore.setPetStoreId(petStoreData.getPetStoreId());
        petStore.setStoreName(petStoreData.getStoreName());
        petStore.setStoreAddress(petStoreData.getStoreAddress());
        petStore.setStoreCity(petStoreData.getStoreCity());
        petStore.setStoreState(petStoreData.getStoreState());
        petStore.setStoreZip(petStoreData.getStoreZip());
        petStore.setStorePhone(petStoreData.getStorePhone());
        return petStore;
    }
}
