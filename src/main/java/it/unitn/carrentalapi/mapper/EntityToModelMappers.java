package it.unitn.carrentalapi.mapper;

import it.unitn.carrental.openapi.model.*;
import it.unitn.carrentalapi.model.*;
import it.unitn.carrentalapi.repository.ImageRepository;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;
import org.openapitools.jackson.nullable.JsonNullable;

import java.util.ArrayList;
import java.util.List;


@Mapper(componentModel = "spring", uses= ImageRepository.class)
public interface EntityToModelMappers {

    @Named("stringToJsonNullable")
    static JsonNullable<String> stringToJsonNullable(String variant) {
        return JsonNullable.of(variant);
    }

    @Named("jsonNullableToString")
    static String jsonNullableToString(JsonNullable<String> jsonNullable) {
        if (jsonNullable == null) {
            return null;
        }
        if (!jsonNullable.isPresent()) {
            return null;
        }

        return jsonNullable.get();
    }

    @Named("userTypeToUserTypeModel")
    static UserTypeModel userTypeToUserTypeModel(UserTypeEntity userType) {

        if (UserTypeModel.ADMIN.getValue().equals(userType.getType())) {
            return UserTypeModel.ADMIN;
        }
        if (UserTypeModel.EMPLOYEE.getValue().equals(userType.getType())) {
            return UserTypeModel.EMPLOYEE;
        }
        if (UserTypeModel.EXTERNAL_API.getValue().equals(userType.getType())) {
            return UserTypeModel.EXTERNAL_API;
        }

        return UserTypeModel.ADMIN;
    }

    @Named("imageToImageId")
    static List<Long> imageToImageId(List<ImageEntity> imageList) {

        List<Long> ids = new ArrayList<>();
        for (ImageEntity image : imageList) {
            ids.add(image.getId());
        }

        return ids;
    }

    @Named("imageIModelImage")
    static List<ImageEntity> imageIModelImage(List<Long> imageIds) {

        ImageRepository imageRepository = Mappers.getMapper(ImageRepository.class);

        List<ImageEntity> images = new ArrayList<>();
        for (Long id : imageIds) {
            images.add(imageRepository.getOne(id));
        }

        return images;
    }

    @Mapping(source = "photos", target = "photos",  qualifiedByName = "imageToImageId")
    CarModel carToCarModel(CarEntity car);

    @Mapping(source = "photos", target = "photos",  qualifiedByName = "imageIModelImage")
    CarEntity carModelToCar(CarModel carModel);

    @Mapping(target = "model", ignore = true )
    @Mapping(target = "equipment", ignore = true )
    @Mapping(target = "photos", ignore = true )
    CarEntity putCarModelToCar(CarRequestModel carRequest);


    CarOverviewModel carToCarOverviewModel(CarEntity car);

    BrandModel branModelBrandModel(BrandEntity brand);
    CarTypeModel carTypeToCarTypeModel(CarTypeEntity carType);
    EquipmentPieceModel equipmentPieceToEquipmentPieceModel(EquipmentPieceEntity equipmentPiece);
    FuelTypeModel fuelTypeToFuelTypeModel(FuelTypeEntity fuelType);

    @Mapping(source = "variant", target = "variant",  qualifiedByName = "stringToJsonNullable")
    CarModelModel modelToModelModel(ModelEntity model);

    @Mapping(source = "variant", target = "variant",  qualifiedByName = "jsonNullableToString")
    ModelEntity modelModelToModel(CarModelModel modelModel);

    @Mapping(source = "variant", target = "variant",  qualifiedByName = "jsonNullableToString")
    ModelEntity modelRequestToModel(CarModelRequestModel modelRequest);

    @Mapping(source = "variant", target = "variant",  qualifiedByName = "stringToJsonNullable")
    CarModelRequestModel modelToModelRequest(ModelEntity model);

    @Mapping(source = "details", target = "details",  qualifiedByName = "stringToJsonNullable")
    ReservationModel reservationToReservationModel(ReservationEntity reservation);

    @Mapping(source = "details", target = "details",  qualifiedByName = "jsonNullableToString")
    ReservationEntity putReservationModelToReservation(ReservationRequestModel reservationRequest);

    @Mapping(source = "userType", target = "userType",  qualifiedByName = "userTypeToUserTypeModel")
    UserModel userToUserModel(UserEntity user);

    CustomerModel customerToCustomerModel(CustomerEntity customer);

    ImageModel imageToImageModel(ImageEntity image);
    ImageEntity imageModelToImage(ImageModel imageModel);
}
