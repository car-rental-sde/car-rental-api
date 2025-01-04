package it.unitn.carrentalapi.mapper;

import it.unitn.carrentalapi.openapi.model.*;
import it.unitn.carrentalapi.entity.*;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.openapitools.jackson.nullable.JsonNullable;


@Mapper(componentModel = "spring")
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

//    @Named("userTypeToUserTypeModel")
//    static UserTypeModel userTypeToUserTypeModel(UserTypeEntity userType) {
//
//        if (UserTypeModel.ADMIN.getValue().equals(userType.getType())) {
//            return UserTypeModel.ADMIN;
//        }
//        if (UserTypeModel.EMPLOYEE.getValue().equals(userType.getType())) {
//            return UserTypeModel.EMPLOYEE;
//        }
//        if (UserTypeModel.EXTERNAL_API.getValue().equals(userType.getType())) {
//            return UserTypeModel.EXTERNAL_API;
//        }
//
//        return UserTypeModel.ADMIN;
//    }

    CarModel carToCarModel(CarEntity car);

    CarEntity carModelToCar(CarModel carModel);

    @Mapping(target = "model", ignore = true )
    @Mapping(target = "equipment", ignore = true )
    CarEntity putCarModelToCar(CarRequestModel carRequest);


    CarOverviewModel carToCarOverviewModel(CarEntity car);

    BrandModel brandModelBrandModel(BrandEntity brand);
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

    ReservationModel reservationToReservationModel(ReservationEntity reservation);

    ReservationEntity putReservationModelToReservation(ReservationRequestModel reservationRequest);

//    @Mapping(source = "userType", target = "userType",  qualifiedByName = "userTypeToUserTypeModel")
    UserModel userToUserModel(UserEntity user);

    CustomerModel customerToCustomerModel(CustomerEntity customer);
}
