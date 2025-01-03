package it.unitn.carrentalapi.repository;

import it.unitn.carrentalapi.entity.ReservationEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

@Repository
public interface ReservationRepository extends JpaRepository<ReservationEntity, Long> {

    @EntityGraph(attributePaths = {"car.model", "car.model.brand"})
    @Query(value = "select r from ReservationEntity r " +
            "where r.beginDate > :startDate " +
            "and r.endDate < :endDate " +
            "and r.beginPlace like :startPlace " +
            "and r.endPlace like :endPlace " +
            "and (:carId = null or r.car.id = :carId) " ,

            countQuery = "select count(*) from ReservationEntity r " +
                    "where r.beginDate > :startDate " +
                    "and r.endDate < :endDate " +
                    "and r.beginPlace like :startPlace " +
                    "and r.endPlace like :endPlace " +
                    "and (:carId = null or r.car.id = :carId) ")
    Page<ReservationEntity> searchReservations(@Param("carId") Long carId,
                                         @Param("startDate") LocalDate startDate,
                                         @Param("endDate") LocalDate endDate,
                                         @Param("startPlace") String startPlace,
                                         @Param("endPlace") String endPlace,
                                         Pageable pageable);
}
