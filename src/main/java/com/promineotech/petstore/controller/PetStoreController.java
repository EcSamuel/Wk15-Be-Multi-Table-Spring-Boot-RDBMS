package com.promineotech.petstore.controller;

import com.promineotech.petstore.controller.model.PetStoreCustomer;
import com.promineotech.petstore.controller.model.PetStoreData;
import com.promineotech.petstore.controller.model.PetStoreEmployee;
import com.promineotech.petstore.entity.PetStore;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.promineotech.petstore.service.PetStoreService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/pet_store")
@Slf4j
public class PetStoreController {

    @Autowired
    private PetStoreService petStoreService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PetStoreData createPetStore(@RequestBody PetStoreData petStoreData) {
        log.info("Received request to create pet store: {}", petStoreData);
        return petStoreService.savePetStore(petStoreData);
    }

    @PostMapping("/{petStoreId}/customers")
    @ResponseStatus(HttpStatus.CREATED)
    public PetStoreCustomer addCustomerToPetStore(
            @PathVariable Long petStoreId,
            @RequestBody PetStoreCustomer petStoreCustomer) {
        return petStoreService.savePetStoreCustomer(petStoreId, petStoreCustomer);
    }

    @PutMapping("/{petStoreId}")
    public PetStoreData updatePetStore(@PathVariable Long petStoreId, @RequestBody PetStoreData petStoreData) {
        log.info("Updating pet store with ID: {}", petStoreId);
        petStoreData.setPetStoreId(petStoreId);
        return petStoreService.savePetStore(petStoreData);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.ACCEPTED)
    public List<PetStoreData> getAllPetStores() {
        log.info("Received request to get pet stores.");
        return petStoreService.getAllPetStores();
    }

    @GetMapping("/{petStoreId}")
    public ResponseEntity<PetStoreData> getPetStoreById(@PathVariable Long petStoreId) {
        log.info("Received request to get pet store with ID: {}", petStoreId);
        try {
            PetStoreData petStoreData = petStoreService.findPetStoreById(petStoreId);
            return ResponseEntity.ok(petStoreData);
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @PostMapping("/{petStoreId}/employee")
    @ResponseStatus(HttpStatus.CREATED)
    public PetStoreEmployee addEmployee(@PathVariable Long petStoreId, @RequestBody PetStoreEmployee petStoreEmployee) {
        log.info("Received request to add employee to pet store: {}", petStoreId);
        return petStoreService.savePetStoreEmployee(petStoreId, petStoreEmployee);
    }

    @DeleteMapping("/{petStoreId}")
    public Map<String, String> deletePetStoreById(@PathVariable Long petStoreId) {
        log.info("Received request to delete pet store with ID: {}", petStoreId);

        petStoreService.deletePetStoreById(petStoreId);

        Map<String, String> response = new HashMap<>();
        response.put("message", "Pet store with ID " + petStoreId + " was successfully deleted.");

        return response;
    }

}

