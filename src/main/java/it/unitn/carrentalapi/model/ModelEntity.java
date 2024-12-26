package it.unitn.carrentalapi.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ModelEntity
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name="brandId", referencedColumnName="id", nullable=false)
    private BrandEntity brand;

    @Column(nullable = false)
    private String name;

    @Column(nullable = true)
    private String variant;

    @Column(nullable = false)
    private Integer productionYear;

    @ManyToOne
    @JoinColumn(name="carTypeId", referencedColumnName="id", nullable=false)
    private CarTypeEntity carType;

    @ManyToOne
    @JoinColumn(name="fuelTypeId", referencedColumnName="id", nullable=false)
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
