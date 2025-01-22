package it.unitn.carrentalapi.graphql.carsexample;

import it.unitn.carrentalapi.mapper.EntityToModelMappers;
import it.unitn.carrentalapi.openapi.model.*;
import it.unitn.carrentalapi.service.InternalService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
public class InternalController {

    private final InternalService internalService;
    private final EntityToModelMappers mappers;

    @QueryMapping
    public List<BrandModel> getBrands() {
        log.debug("Getting all brands");
        return internalService.getBrands().stream()
                .map(mappers::brandModelBrandModel)
                .toList();
    }

    @QueryMapping
    public List<CarTypeModel> getCarTypes() {
        log.debug("Getting all car types");
        return internalService.getCarTypes().stream()
                .map(mappers::carTypeToCarTypeModel)
                .toList();
    }

    @QueryMapping
    public List<EquipmentPieceModel> getEquipmentPieces() {
        log.debug("Getting all equipment pieces");
        return internalService.getEquipmentPieces().stream()
                .map(mappers::equipmentPieceToEquipmentPieceModel)
                .toList();
    }

    @QueryMapping
    public List<FuelTypeModel> getFuelTypes() {
        log.debug("Getting all fuel types");
        return internalService.getFuelTypes().stream()
                .map(mappers::fuelTypeToFuelTypeModel)
                .toList();
    }

    @QueryMapping
    public List<CarModelModel> getCarModels() {
        log.debug("Getting all car models");
        return internalService.getModels().stream()
                .map(mappers::modelToModelModel)
                .toList();
    }

    @QueryMapping
    public List<CarModelModel> getModelsByBrand(@Argument Long brandId) {
        log.debug("Getting all car models by brand id: [{}]", brandId);
        return internalService.getModelsByBrands(brandId).stream()
                .map(mappers::modelToModelModel)
                .toList();
    }

    @MutationMapping
    public CarModelModel addCarModel(@Argument CarModelRequestModel carModelRequest) {
        log.debug("Adding new car model: [{}]", carModelRequest);
        return mappers.modelToModelModel(internalService.addModel(carModelRequest));
    }
}
