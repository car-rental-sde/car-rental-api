package it.unitn.carrentalapi.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "model")
public class CarModelEntity
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name="brand_id", referencedColumnName="id", nullable=false)
    private BrandEntity brand;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private Integer productionYear;

    @ManyToOne
    @JoinColumn(name="car_type_id", referencedColumnName="id", nullable=false)
    private CarTypeEntity carType;

    @ManyToOne
    @JoinColumn(name="fuel_type_id", referencedColumnName="id", nullable=false)
    private FuelTypeEntity fuelType;

    @Column(nullable = false)
    private Boolean isGearboxAutomatic;

    @Column(nullable = false)
    private Integer numberOfDoors;

    @Column(nullable = false)
    private Integer numberOfSeats;

    @Column(nullable = false)
    private Integer trunkCapacity;

    @Column(nullable = false)
    private Integer horsePower;

    @Column(nullable = false)
    private Float avgFuelConsumption;
}
