package it.unitn.carrentalapi.repository;

import it.unitn.carrentalapi.entity.FuelTypeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FuelTypeRepository extends JpaRepository<FuelTypeEntity, Long> {
}
