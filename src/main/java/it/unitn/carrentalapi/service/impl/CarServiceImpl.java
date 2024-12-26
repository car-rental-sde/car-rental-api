package it.unitn.carrentalapi.service.impl;

import it.unitn.carrental.openapi.model.CarRequestModel;
import it.unitn.carrental.openapi.model.CarsSortColumn;
import it.unitn.carrental.openapi.model.SortDirection;
import it.unitn.carrentalapi.mapper.EntityToModelMappers;
import it.unitn.carrentalapi.model.CarEntity;
import it.unitn.carrentalapi.model.EquipmentPieceEntity;
import it.unitn.carrentalapi.model.ImageEntity;
import it.unitn.carrentalapi.repository.CarRepository;
import it.unitn.carrentalapi.repository.EquipmentPieceRepository;
import it.unitn.carrentalapi.repository.ImageRepository;
import it.unitn.carrentalapi.repository.ModelRepository;
import it.unitn.carrentalapi.service.CarService;
import it.unitn.carrentalapi.service.utils.ServiceUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class CarServiceImpl implements CarService {

    private final CarRepository carRepository;
    private final ModelRepository modelRepository;
    private final EquipmentPieceRepository equipmentPieceRepository;
    private final ImageRepository imageRepository;
    private final EntityToModelMappers mappers;

    @Override
    public Page<CarEntity> searchCars(String brand,
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
                                      Integer size) {

        brand = ServiceUtils.addSqlWildcards(brand);
        model = ServiceUtils.addSqlWildcards(model);
        carType = ServiceUtils.addSqlWildcards(carType);
        fuelType = ServiceUtils.addSqlWildcards(fuelType);
        if (startDate == null) {
            startDate = LocalDate.now().plusDays(1);
        }
        if (endDate == null) {
            endDate = LocalDate.now().minusDays(1);
        }
        if (sortBy == null) {
            sortBy = CarsSortColumn.ID;
        }
        if (sortDirection == null) {
            sortDirection = SortDirection.ASC;
        }

        Pageable pageRequest;
        Sort.Direction direction = SortDirection.ASC.equals(sortDirection) ?
                Sort.Direction.ASC : Sort.Direction.DESC;
        pageRequest = switch (sortBy) {
            case MODEL -> PageRequest.of(page - 1, size, direction, "model.name");
            case BRAND -> PageRequest.of(page - 1, size, direction, "model.brand.name");
            case CAR_TYPE -> PageRequest.of(page - 1, size, direction, "model.carType.name");
            case IS_GEARBOX_AUTOMATIC -> PageRequest.of(page - 1, size, direction, "model.isGearboxAutomatic");
            case FUEL_TYPE -> PageRequest.of(page - 1, size, direction, "model.fuelType.name");
            case NUMBER_OF_SEATS -> PageRequest.of(page - 1, size, direction, "model.numberOfSeats");
            case PRODUCTION_YEAR -> PageRequest.of(page - 1, size, direction, "model.productionYear");
            case BEGIN_PLACE -> PageRequest.of(page - 1, size, direction, "model.beginPlace");
            default -> PageRequest.of(page - 1, size, direction, sortBy.getValue());
        };


        // TODO: beginPosition czy beginPlace?
        return carRepository.searchCars(brand, model, carType, fuelType, isGearboxAutomatic, seatsMin, seatsMax, yearMin, yearMax, dayPriceMin, dayPriceMax, startDate, endDate, pageRequest);
    }

    @Override
    public CarEntity addCar(CarRequestModel carRequest) {

        CarEntity car = mappers.putCarModelToCar(carRequest);
        car.setModel(modelRepository.getById(carRequest.getModelId()));
        car.setAdditionDate(LocalDate.now());

        List<EquipmentPieceEntity> equipmentPieceList = new ArrayList<>();
        for (Long eqId : carRequest.getEquipment()) {
            equipmentPieceList.add(equipmentPieceRepository.getById(eqId));
        }
        car.setEquipment( equipmentPieceList );

        List<ImageEntity> imageList = new ArrayList<>();
        for (Long imageId : carRequest.getPhotos()) {
            imageList.add(imageRepository.getById(imageId));
        }
        car.setPhotos( imageList );

        return carRepository.save(car);
    }

    @Override
    public void deleteCar(Long id) {
        carRepository.deleteById(id);
    }

    @Override
    public Optional<CarEntity> getCar(Long id) {
        return carRepository.findById(id);
    }

    @Override
    public CarEntity updateCar(Long id, CarRequestModel carRequest) {
        CarEntity car = carRepository.getById(id);
        car.setModel(modelRepository.getById(carRequest.getModelId()));
        car.setMileage(carRequest.getMileage());

        List<EquipmentPieceEntity> equipmentPieceList = car.getEquipment();
        for (Long eqId : carRequest.getEquipment()) {
            equipmentPieceList.add(equipmentPieceRepository.getById(eqId));
        }

        car.setDayPrice(carRequest.getDayPrice());
        car.setColor(carRequest.getColor());

        List<ImageEntity> imageList = car.getPhotos();
        for (Long imageId : carRequest.getPhotos()) {
            imageList.add(imageRepository.getById(imageId));
        }

        return carRepository.save(car);
    }
}