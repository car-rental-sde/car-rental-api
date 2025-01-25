package it.unitn.carrentalapi.graphql.carsexample.model;

import it.unitn.carrentalapi.openapi.model.CarModelModel;
import it.unitn.carrentalapi.openapi.model.EquipmentPieceModel;
import it.unitn.carrentalapi.openapi.model.ReservationModel;
import jakarta.validation.Valid;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class Car {
    private Long id;
    private CarModelModel model;
    private Integer mileage;
    private List<EquipmentPieceModel> equipment = new ArrayList<>();
    private Long dayPrice;
    private String currency;
    private String color;
    private List<ReservationModel> reservations = new ArrayList<>();
}
