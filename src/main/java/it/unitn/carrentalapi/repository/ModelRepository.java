package it.unitn.carrentalapi.repository;

import it.unitn.carrentalapi.entity.ModelEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ModelRepository extends JpaRepository<ModelEntity, Long> {

    List<ModelEntity> findAllByBrandId(Long brandId);
}
