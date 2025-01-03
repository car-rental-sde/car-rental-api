package it.unitn.carrentalapi.repository;

import it.unitn.carrentalapi.entity.CarTypeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CarTypeRepository extends JpaRepository<CarTypeEntity, Long> {
}
