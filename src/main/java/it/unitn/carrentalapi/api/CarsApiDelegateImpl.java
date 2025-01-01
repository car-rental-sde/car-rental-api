package it.unitn.carrentalapi.api;

import it.unitn.carrentalapi.openapi.api.CarsApiDelegate;
import it.unitn.carrentalapi.openapi.model.*;
import it.unitn.carrentalapi.entity.CarEntity;
import it.unitn.carrentalapi.mapper.EntityToModelMappers;
import it.unitn.carrentalapi.service.CarService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Slf4j
@Component
@RequiredArgsConstructor
public class CarsApiDelegateImpl implements CarsApiDelegate {

    private final CarService carService;

    private final EntityToModelMappers mappers;

    @Override
    public ResponseEntity<CarsPaginationResponseModel> searchCars(String brand, String model, String carType, String fuelType,
                                                                  Boolean isGearboxAutomatic, Integer seatsMin, Integer seatsMax,
                                                                  Integer yearMin, Integer yearMax, Long dayPriceMin, Long dayPriceMax,
                                                                  LocalDate startDate, LocalDate endDate, String place,
                                                                  CarsSortColumn sortBy, SortDirection sortDirection,
                                                                  Integer page, Integer size) {
        log.info("Retrieving cars with filters: carType - [{}], dayPriceMin - [{}], dayPriceMax - [{}], startDate, endDate - [{}], place - [{}], sortBy - [{}], sortDirection - [{}], sortDirection - [{}], sortDirection - [{}], size - [{}]", carType, dayPriceMin, dayPriceMax, startDate, endDate, place, sortBy, sortDirection, page, size);

        Page<CarEntity> cars = carService.searchCars(brand, model, carType, fuelType, isGearboxAutomatic, seatsMin, seatsMax, yearMin, yearMax, dayPriceMin, dayPriceMax, startDate, endDate, place, sortBy, sortDirection, page, size);

        CarsPaginationResponseModel response = new CarsPaginationResponseModel();
        response.setCars(cars.stream().map(mappers::carToCarModel).toList());
        response.setPageNumber(cars.getNumber() + 1);
        response.setPageSize(cars.getSize());
        response.setTotalRecords(cars.getTotalElements());

        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<CarModel> addCar(CarRequestModel carRequestModel) {
        return CarsApiDelegate.super.addCar(carRequestModel);
    }

    @Override
    public ResponseEntity<CarModel> getCar(Long id) {
        return CarsApiDelegate.super.getCar(id);
    }

    @Override
    public ResponseEntity<CarModel> updateCar(Long id, CarRequestModel carRequestModel) {
        return CarsApiDelegate.super.updateCar(id, carRequestModel);
    }

    @Override
    public ResponseEntity<Void> deleteCar(Long id) {
        return CarsApiDelegate.super.deleteCar(id);
    }
}
