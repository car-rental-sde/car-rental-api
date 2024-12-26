package it.unitn.carrentalapi.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity

@Table
@Getter
@Setter
public class CustomerEntity
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;

    @Column(nullable = false, unique = true)
    private Long booklyId;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String surname;

    @Column(nullable = false)
    private Boolean isBlocked;
}
