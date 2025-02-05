package it.unitn.carrentalapi.service.impl;

import it.unitn.carrentalapi.entity.*;
import it.unitn.carrentalapi.mapper.EntityToModelMappers;
import it.unitn.carrentalapi.openapi.model.CarModelRequestModel;
import it.unitn.carrentalapi.repository.*;
import it.unitn.carrentalapi.service.InternalService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class InternalServiceImpl implements InternalService {

    private final BrandRepository brandRepository;
    private final CarTypeRepository carTypeRepository;
    private final EquipmentPieceRepository equipmentPieceRepository;
    private final FuelTypeRepository fuelTypeRepository;
    private final ModelRepository modelRepository;
    private final EntityToModelMappers mappers;


    @Override
    public List<BrandEntity> getBrands() {
        return brandRepository.findAll();
    }

    @Override
    public List<CarTypeEntity> getCarTypes() {
        return carTypeRepository.findAll();    }

    @Override
    public List<EquipmentPieceEntity> getEquipmentPieces() {
        return equipmentPieceRepository.findAll();
    }

    @Override
    public List<FuelTypeEntity> getFuelTypes() {
        return fuelTypeRepository.findAll();
    }

    @Override
    public List<CarModelEntity> getModels() {
        return modelRepository.findAll();
    }

    @Override
    public List<CarModelEntity> getModelsByBrands(Long brandId) {
        return modelRepository.findAllByBrandId(brandId);
    }

    @Override
    public CarModelEntity addModel(CarModelRequestModel modelRequest) {

        CarModelEntity model = mappers.modelRequestToModel(modelRequest);
        model.setBrand(brandRepository.getReferenceById(modelRequest.getBrandId()));
        model.setCarType(carTypeRepository.getReferenceById(modelRequest.getCarTypeId()));
        model.setFuelType(fuelTypeRepository.getReferenceById(modelRequest.getFuelTypeId()));

        return modelRepository.save(model);
    }
}
