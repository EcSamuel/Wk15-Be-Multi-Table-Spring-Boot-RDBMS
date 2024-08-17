package pet.store.service;

import com.promineotech.petstore.entity.PetStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pet.store.controller.model.PetStoreData;
import pet.store.dao.PetStoreDao;

import java.util.NoSuchElementException;

public class PetStoreService {
    private PetStoreDao petStoreDao;

    public PetStoreData savePetStore(PetStoreData petStoreData) {
        PetStore petStore = findOrCreatePetStore(petStoreData.getId());

        copyPetStoreFields(petStore, petStoreData);

        PetStore savedPetStore = petStoreDao.save(petStore);

        return new PetStoreData(savedPetStore);
    }

    private PetStore findOrCreatePetStore(Long petStoreId) {
        if (petStoreId == null) {
            return new PetStore();
        } else {
            return petStoreDao.findById(petStoreId).orElseThrow(() ->
                new NoSuchElementException("Pet Store with Id " + petStoreId + " cannot be found!");
        }
    }

    private void copyPetStoreFields(PetStore petStore, PetStoreData petStoreData) {
        petStore.setStoreName(petStoreData.getStoreName());
        petStore.setStoreAddress(petStoreData.getStoreAddress());
    }
}
