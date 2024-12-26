package it.unitn.carrentalapi.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "car")
public class CarEntity
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;

    @Column(nullable = false)
    private LocalDate additionDate;

    @ManyToOne
    @JoinColumn(name="model_id", referencedColumnName="id", nullable=false)
    private ModelEntity model;

    @Column(nullable = false)
    private Integer mileage;

    @ManyToMany
    @JoinTable(name="equipment_2_car", joinColumns=@JoinColumn(name="car_id"), inverseJoinColumns=@JoinColumn(name="equipment_id"))
    private List<EquipmentPieceEntity> equipment;

    @Column(nullable = false)
    private Long dayPrice;

    @Column(nullable = false)
    private String color;
}
