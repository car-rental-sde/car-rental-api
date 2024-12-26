package it.unitn.carrentalapi.service;

import it.unitn.carrental.openapi.model.CarRequestModel;
import it.unitn.carrental.openapi.model.CarsSortColumn;
import it.unitn.carrental.openapi.model.SortDirection;
import it.unitn.carrentalapi.model.CarEntity;
import org.springframework.data.domain.Page;

import java.time.LocalDate;
import java.util.Optional;

public interface CarService {

    Page<CarEntity> searchCars(String brand,
                               String model,
                               String carType,
                               String fuelType,
                               Boolean isGearboxAutomatic,
                               Integer seatsMin,
                               Integer seatsMax,
                               Integer yearMin,
                               Integer yearMax,
                               Long dayPriceMin,
                               Long dayPriceMax,
                               LocalDate startDate,
                               LocalDate endDate,
                               String place,
                               CarsSortColumn sortBy,
                               SortDirection sortDirection,
                               Integer page,
                               Integer size);

    CarEntity addCar(CarRequestModel carRequest);
    Optional<CarEntity> getCar(Long id);
    CarEntity updateCar(Long id, CarRequestModel carRequest);
    void deleteCar(Long id);
}
