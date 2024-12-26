package it.unitn.carrentalapi.repository;

import it.unitn.carrentalapi.model.ImageEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageRepository extends JpaRepository<ImageEntity, Long> {
}
