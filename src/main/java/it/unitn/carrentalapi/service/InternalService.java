package it.unitn.carrentalapi.service;

import it.unitn.carrentalapi.entity.*;
import it.unitn.carrentalapi.openapi.model.CarModelRequestModel;

import java.util.List;

public interface InternalService {
    List<BrandEntity> getBrands();
    List<CarTypeEntity> getCarTypes();
    List<EquipmentPieceEntity> getEquipmentPieces();
    List<FuelTypeEntity> getFuelTypes();
    List<CarModelEntity> getModels();
    List<CarModelEntity> getModelsByBrands(Long brandId);
    CarModelEntity addModel(CarModelRequestModel modelRequest);
}
