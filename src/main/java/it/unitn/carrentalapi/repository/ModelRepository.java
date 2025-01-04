package it.unitn.carrentalapi.repository;

import it.unitn.carrentalapi.entity.CarModelEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ModelRepository extends JpaRepository<CarModelEntity, Long> {

    List<CarModelEntity> findAllByBrandId(Long brandId);
}
