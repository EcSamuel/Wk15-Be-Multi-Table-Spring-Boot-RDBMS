package com.promineotech.petstore.service;

import com.promineotech.petstore.dao.PetStoreDao;
import com.promineotech.petstore.entity.PetStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.promineotech.petstore.controller.model.PetStoreData;

import java.util.NoSuchElementException;

@Service
public class PetStoreService {

    @Autowired
    private PetStoreDao petStoreDao;

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
    }
}
