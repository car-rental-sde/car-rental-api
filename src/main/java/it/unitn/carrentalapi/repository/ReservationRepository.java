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
    @Query(value = """
            select r from ReservationEntity r
            where r.beginDate > :startDate
            and r.endDate < :endDate
            and (:carId is null or r.car.id = :carId)
            and (:customerExternalId is null or r.customer.externalId = :customerExternalId)
            """,

            countQuery = """
                    select count(*) from ReservationEntity r
                    where r.beginDate > :startDate
                    and r.endDate < :endDate
                    and (:carId is null or r.car.id = :carId)
                    and (:customerExternalId is null or r.customer.externalId = :customerExternalId)
            """)
    Page<ReservationEntity> searchReservations(
            @Param("customerExternalId") Long customerExternalId,
            @Param("carId") Long carId,
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate,
            Pageable pageable
    );
}
