package it.unitn.carrentalapi.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CarEntity
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;

    @Column(nullable = false)
    private LocalDate additionDate;

    @ManyToOne
    @JoinColumn(name="modelId", referencedColumnName="id", nullable=false)
    private ModelEntity model;

    @Column(nullable = false)
    private Integer mileage;

    @ManyToMany
    @JoinTable(name="equipments2Cars", joinColumns=@JoinColumn(name="carId"), inverseJoinColumns=@JoinColumn(name="equipmentId"))
    private List<EquipmentPieceEntity> equipment;

    @Column(nullable = false)
    private Long dayPrice;

    @Column(nullable = false)
    private String color;

    @OneToMany
    @JoinColumn(name="car_id", nullable=true)
    private List<ImageEntity> photos;
}
