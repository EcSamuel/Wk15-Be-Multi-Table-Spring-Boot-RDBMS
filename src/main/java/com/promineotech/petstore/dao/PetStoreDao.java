package com.promineotech.petstore.dao;

import com.promineotech.petstore.entity.PetStore;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PetStoreDao extends JpaRepository<PetStore, Long> {
    // No need to add any methods here unless you need custom queries
}