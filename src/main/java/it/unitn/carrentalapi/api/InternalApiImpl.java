package it.unitn.carrentalapi.api;

import it.unitn.carrentalapi.mapper.EntityToModelMappers;
import it.unitn.carrentalapi.openapi.api.InternalApiDelegate;
import it.unitn.carrentalapi.openapi.model.*;
import it.unitn.carrentalapi.service.InternalService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class InternalApiImpl implements InternalApiDelegate {

    private final InternalService internalService;
    private final EntityToModelMappers mappers;

    @Override
    public ResponseEntity<List<BrandModel>> getBrands() {
        log.debug("Getting all brands");
        return ResponseEntity.ok(internalService.getBrands().stream().map(mappers::brandModelBrandModel).toList());
    }

    @Override
    public ResponseEntity<List<CarTypeModel>> getCarTypes() {
        log.debug("Getting all car types");
        return ResponseEntity.ok(internalService.getCarTypes().stream().map(mappers::carTypeToCarTypeModel).toList());
    }

    @Override
    public ResponseEntity<List<EquipmentPieceModel>> getEquipmentPieces() {
        log.debug("Getting all equipment pieces");
        return ResponseEntity.ok(internalService.getEquipmentPieces().stream().map(mappers::equipmentPieceToEquipmentPieceModel).toList());
    }

    @Override
    public ResponseEntity<List<FuelTypeModel>> getFuelTypes() {
        log.debug("Getting all fuel types");
        return ResponseEntity.ok(internalService.getFuelTypes().stream().map(mappers::fuelTypeToFuelTypeModel).toList());
    }

    @Override
    public ResponseEntity<List<CarModelModel>> getModels() {
        log.debug("Getting all car models");
        return ResponseEntity.ok(internalService.getModels().stream().map(mappers::modelToModelModel).toList());
    }

    @Override
    public ResponseEntity<List<CarModelModel>> getModelsByBrands(Long brandId) {
        log.debug("Getting all car models by brand id: [{}]", brandId);
        return ResponseEntity.ok(internalService.getModelsByBrands(brandId).stream().map(mappers::modelToModelModel).toList());
    }

    @Override
    public ResponseEntity<CarModelModel> addModel(CarModelRequestModel carModelRequestModel) {
        log.debug("Adding new car model: [{}]", carModelRequestModel);
        return ResponseEntity.ok(mappers.modelToModelModel(internalService.addModel(carModelRequestModel)));
    }
}
