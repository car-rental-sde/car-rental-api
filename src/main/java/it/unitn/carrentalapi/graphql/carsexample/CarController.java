package it.unitn.carrentalapi.graphql.carsexample;

import it.unitn.carrentalapi.entity.CarEntity;
import it.unitn.carrentalapi.entity.ReservationEntity;
import it.unitn.carrentalapi.facade.CurrencyExchangeFacade;
import it.unitn.carrentalapi.mapper.EntityToModelMappers;
import it.unitn.carrentalapi.openapi.model.*;
import it.unitn.carrentalapi.service.CarService;
import it.unitn.carrentalapi.service.ReservationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.stereotype.Controller;

import java.time.LocalDate;
import java.util.Currency;
import java.util.List;
import java.util.Optional;

@Slf4j
@Controller
@RequiredArgsConstructor
public class CarController {

    private final CarService carService;
    private final ReservationService reservationService;
    private final EntityToModelMappers mappers;
    private final CurrencyExchangeFacade currencyExchangeFacade;

    @QueryMapping
    public CarsPaginationResponseModel searchCars(
            @Argument String brand,
            @Argument String model,
            @Argument String carType,
            @Argument String fuelType,
            @Argument Boolean isGearboxAutomatic,
            @Argument Integer seatsMin,
            @Argument Integer seatsMax,
            @Argument Integer yearMin,
            @Argument Integer yearMax,
            @Argument Long dayPriceMin,
            @Argument Long dayPriceMax,
            @Argument LocalDate startDate,
            @Argument LocalDate endDate,
            @Argument String place,
            @Argument CarsSortColumn sortBy,
            @Argument SortDirection sortDirection,
            @Argument Integer page,
            @Argument Integer size
    ) {
        log.info("Retrieving cars with filters: carType - [{}], dayPriceMin - [{}], dayPriceMax - [{}], " +
                "startDate - [{}], endDate - [{}], place - [{}], sortBy - [{}], sortDirection - [{}], page - [{}], size - [{}]",
                carType, dayPriceMin, dayPriceMax, startDate, endDate, place, sortBy, sortDirection, page, size);

        Page<CarEntity> cars = carService.searchCars(brand, model, carType, fuelType, isGearboxAutomatic, seatsMin, seatsMax, yearMin, yearMax, dayPriceMin, dayPriceMax, startDate, endDate, place, sortBy, sortDirection, page, size);

        CarsPaginationResponseModel response = new CarsPaginationResponseModel();
        response.setCars(cars.stream().map(mappers::carToCarOverviewModel).toList());
        response.setPageNumber(cars.getNumber() + 1);
        response.setPageSize(cars.getSize());
        response.setTotalRecords(cars.getTotalElements());

        return response;
    }

    @MutationMapping
    public CarModel addCar(@Argument CarRequestModel carRequest) {
        log.debug("Adding car with request: [{}]", carRequest);

        return mappers.carToCarModel(carService.addCar(carRequest));
    }

    @QueryMapping
    public CarModel getCar(@Argument Long id, @Argument String currency) {
        log.debug("Getting car with id: [{}]", id);

        Optional<CarEntity> optionalCar = carService.getCar(id);

        if (optionalCar.isEmpty()) {
            return null;
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
                    return carModel;
                }

                carModel.setCurrency(currency);
                carModel.setDayPrice(convertedOptional.get());
            } catch (Exception e) {
                log.warn("Currency [{}] is not valid, using default currency EUR", currency);
            }
        }

        return carModel;
    }

    @SchemaMapping(typeName = "Car", field = "reservations")
    public List<ReservationEntity> getReservations(CarModel car) {
        log.debug("Fetching reservations for car with id: [{}]", car.getId());
        return reservationService.getReservationsByCarId(car.getId());
    }

    @MutationMapping
    public CarModel updateCar(@Argument Long id, @Argument CarRequestModel carRequest) {
        log.debug("Updating car with id: [{}]", id);

        return mappers.carToCarModel(carService.updateCar(id, carRequest));
    }

    @MutationMapping
    public Boolean deleteCar(@Argument Long id) {
        log.debug("Deleting car with id: [{}]", id);

        carService.deleteCar(id);
        return true;
    }
}
