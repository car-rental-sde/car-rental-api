package it.unitn.carrentalapi.service;

import it.unitn.carrentalapi.entity.*;
import it.unitn.carrentalapi.openapi.model.CarModelRequestModel;

import java.util.List;

public interface InternalService {
    List<BrandEntity> getBrands();
    List<CarTypeEntity> getCarTypes();
    List<EquipmentPieceEntity> getEquipmentPieces();
    List<FuelTypeEntity> getFuelTypes();
    List<ModelEntity> getModels();
    List<ModelEntity> getModelsByBrands(Long brandId);
    ModelEntity addModel(CarModelRequestModel modelRequest);
}
