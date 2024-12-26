package it.unitn.carrentalapi.repository;

import it.unitn.carrentalapi.entity.CarEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

@Repository
public interface CarRepository extends JpaRepository<CarEntity, Long> {

//    SELECT DISTINCT c.id, c.*
//    FROM Car c
//    LEFT JOIN Reservation r ON r.car_id = c.id
//    JOIN Model m ON m.Id = c.model_id
//    JOIN car_type ct ON ct.id = m.car_type_id
//    WHERE ct.name LIKE '%'
//    AND c.day_price >= 0
//    AND c.day_price <= 1000
//    AND c.id NOT IN (SELECT c.id FROM Car c
//            LEFT JOIN Reservation r ON r.car_id = c.id
//            WHERE r.begin_date >= '2023-01-26' AND r.end_date <= '2023-01-30')

    @EntityGraph(attributePaths = {"model", "model.brand"})
    @Query(value = "select c from CarEntity c " +
            "where c.model.carType.name like :carType " +
            "and c.model.fuelType.name like :fuelType " +
            "and c.model.brand.name like :brand " +
            "and c.model.name like :model " +
            "and (:isGearboxAutomatic = null or c.model.isGearboxAutomatic = :isGearboxAutomatic) " +
            "and c.model.numberOfSeats >= :seatsMin " +
            "and c.model.numberOfSeats <= :seatsMax " +
            "and c.model.productionYear >= :yearMin " +
            "and c.model.productionYear <= :yearMax " +
            "and c.dayPrice >= :dayPriceMin " +
            "and c.dayPrice <= :dayPriceMax " +
            "and c.id not in (select c.id from CarEntity c " +
            "   join ReservationEntity r on r.car.id = c.id " +
            "   where r.beginDate > :startDate and r.endDate < :endDate)" +
            "", // FIXME: JPQL nie zawiera top ani first

    countQuery = "select count(c.id) from CarEntity c " +
            "where c.model.carType.name like :carType " +
            "and c.model.fuelType.name like :fuelType " +
            "and c.model.brand.name like :brand " +
            "and c.model.name like :model " +
            "and (:isGearboxAutomatic = null or c.model.isGearboxAutomatic = :isGearboxAutomatic) " +
            "and c.model.numberOfSeats >= :seatsMin " +
            "and c.model.numberOfSeats <= :seatsMax " +
            "and c.model.productionYear >= :yearMin " +
            "and c.model.productionYear <= :yearMax " +
            "and c.dayPrice >= :dayPriceMin " +
            "and c.dayPrice <= :dayPriceMax " +
            "and c.id not in (select c.id from CarEntity c " +
            "   join ReservationEntity r on r.car.id = c.id " +
            "   where r.beginDate > :startDate and r.endDate < :endDate)" +
            "")
    Page<CarEntity> searchCars(@Param("brand") String brand,
                               @Param("model") String model,
                               @Param("carType") String carType,
                               @Param("fuelType") String fuelType,
                               @Param("isGearboxAutomatic") Boolean isGearboxAutomatic,
                               @Param("seatsMin") Integer seatsMin,
                               @Param("seatsMax") Integer seatsMax,
                               @Param("yearMin") Integer yearMin,
                               @Param("yearMax") Integer yearMax,
                               @Param("dayPriceMin") Long dayPriceMin,
                               @Param("dayPriceMax") Long dayPriceMax,
                               @Param("startDate") LocalDate startDate,
                               @Param("endDate") LocalDate endDate,
                               Pageable pageable);

    @Query(value = "select c from CarEntity c " +
            "where c.id = :id " +
            "and c.id not in (select c.id from CarEntity c " +
            "   join ReservationEntity r on r.car.id = c.id " +
            "   where r.beginDate >= :startDate and r.endDate <= :endDate)")
    CarEntity getCarIfNoReservation(@Param("id") Long id,
                                    @Param("startDate") LocalDate startDate,
                                    @Param("endDate") LocalDate endDate);
}
