package it.unitn.carrentalapi.api;

import it.unitn.carrentalapi.entity.CarEntity;
import it.unitn.carrentalapi.facade.CurrencyExchangeFacade;
import it.unitn.carrentalapi.mapper.EntityToModelMappers;
import it.unitn.carrentalapi.openapi.api.CarsApiDelegate;
import it.unitn.carrentalapi.openapi.model.*;
import it.unitn.carrentalapi.service.CarService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Currency;
import java.util.Optional;

@Slf4j
@Component
@RequiredArgsConstructor
public class CarsApiDelegateImpl implements CarsApiDelegate {

    private final CarService carService;
    private final EntityToModelMappers mappers;
    private final CurrencyExchangeFacade currencyExchangeFacade;

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
        response.setCars(cars.stream().map(mappers::carToCarOverviewModel).toList());
        response.setPageNumber(cars.getNumber() + 1);
        response.setPageSize(cars.getSize());
        response.setTotalRecords(cars.getTotalElements());

        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<CarModel> addCar(CarRequestModel carRequestModel) {
        log.debug("Adding car with request: [{}]", carRequestModel);

        return ResponseEntity.ok(mappers.carToCarModel(carService.addCar(carRequestModel)));
    }

    @Override
    public ResponseEntity<CarModel> getCar(Long id, String currency) {
        log.debug("Getting car with id: [{}]", id);

        Optional<CarEntity> optionalCar = carService.getCar(id);

        if (optionalCar.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        CarEntity car = optionalCar.get();
        CarModel carModel = mappers.carToCarModel(car);

        if (currency != null && !currency.isBlank() && !currency.equals("EUR")) {
            try {
                Currency.getInstance(currency);

                Long dayPriceEuro = car.getDayPriceEuro();
                Optional<Long> convertedOptional = currencyExchangeFacade.convert(dayPriceEuro, "EUR", currency);

                if (convertedOptional.isEmpty()) {
                    log.warn("Failed to convert day price from EUR to [{}], using default currency EUR", currency);
                    return ResponseEntity.ok(carModel);
                }

                carModel.setCurrency(currency);
                carModel.setDayPrice(convertedOptional.get());
            } catch (Exception e) {
                log.warn("Currency [{}] is not valid, using default currency EUR", currency);
            }
        }

        return ResponseEntity.ok(carModel);
    }

    @Override
    public ResponseEntity<CarModel> updateCar(Long id, CarRequestModel carRequestModel) {
        log.debug("Updating car with id: [{}]", id);

        return ResponseEntity.ok(mappers.carToCarModel(carService.updateCar(id, carRequestModel)));
    }

    @Override
    public ResponseEntity<Void> deleteCar(Long id) {
        log.debug("Deleting car with id: [{}]", id);

        carService.deleteCar(id);
        return ResponseEntity.ok().build();
    }
}
