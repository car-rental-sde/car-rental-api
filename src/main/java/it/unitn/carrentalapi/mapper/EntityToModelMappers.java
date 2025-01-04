package it.unitn.carrentalapi.mapper;

import it.unitn.carrentalapi.entity.*;
import it.unitn.carrentalapi.openapi.model.*;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;


@Mapper(componentModel = "spring")
public interface EntityToModelMappers {

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

    CarModelModel modelToModelModel(CarModelEntity model);

    CarModelEntity modelModelToModel(CarModelModel modelModel);

    CarModelEntity modelRequestToModel(CarModelRequestModel modelRequest);

    CarModelRequestModel modelToModelRequest(CarModelEntity model);

    ReservationModel reservationToReservationModel(ReservationEntity reservation);

    ReservationEntity putReservationModelToReservation(ReservationRequestModel reservationRequest);

//    @Mapping(source = "userType", target = "userType",  qualifiedByName = "userTypeToUserTypeModel")
    UserModel userToUserModel(UserEntity user);

    CustomerModel customerToCustomerModel(CustomerEntity customer);
}
