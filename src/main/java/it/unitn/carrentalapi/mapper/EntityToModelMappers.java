package it.unitn.carrentalapi.mapper;

import it.unitn.carrentalapi.entity.*;
import it.unitn.carrentalapi.openapi.model.*;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;


@Mapper(componentModel = "spring")
public interface EntityToModelMappers {

    @Named("roleToUserRoleModel")
    static UserRoleModel roleToUserRoleModel(UserRole userType) {

        if (UserRoleModel.ADMIN.getValue().equals(userType.name())) {
            return UserRoleModel.ADMIN;
        }
        if (UserRoleModel.USER.getValue().equals(userType.name())) {
            return UserRoleModel.USER;
        }
        if (UserRoleModel.API_CLIENT.getValue().equals(userType.name())) {
            return UserRoleModel.API_CLIENT;
        }

        return UserRoleModel.USER;
    }

    @Mapping(source = "dayPriceEuro", target = "dayPrice")
    @Mapping(target = "currency", constant = "EUR")
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

    @Mapping(target = "currency", constant = "EUR")
    ReservationModel reservationToReservationModel(ReservationEntity reservation);

    ReservationEntity putReservationModelToReservation(ReservationRequestModel reservationRequest);

    @Mapping(source = "role", target = "userRole",  qualifiedByName = "roleToUserRoleModel")
    UserModel userToUserModel(UserEntity user);

    CustomerModel customerToCustomerModel(CustomerEntity customer);
}
