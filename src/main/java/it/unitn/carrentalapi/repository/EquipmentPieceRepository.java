package it.unitn.carrentalapi.repository;

import it.unitn.carrentalapi.model.EquipmentPieceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EquipmentPieceRepository extends JpaRepository<EquipmentPieceEntity, Long> {
}
