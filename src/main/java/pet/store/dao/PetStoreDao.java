package pet.store.dao;

import com.promineotech.petstore.entity.PetStore;
import org.springframework.data.jpa.repository.JpaRepository;

public class PetStoreDao extends JpaRepository<PetStore, Long> {
}
