package it.unitn.carrentalapi.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Entity
@Table
@Data
public class ReservationEntity
{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(nullable = false)
	private Long id;

	@ManyToOne
	@JoinColumn(name="car_id", referencedColumnName="id", nullable=false)
	private CarEntity car;

	@Column(nullable = false)
	private LocalDate beginDate;

	@Column(nullable = false)
	private LocalDate endDate;

	@Column(nullable = false)
	private String beginPlace;

	@Column(nullable = false)
	private String endPlace;

	@Column(nullable = false)
	private String beginPosition;

	@Column(nullable = false)
	private String endPosition;

	@Column(nullable = false)
	private Boolean isMaintenance;

	@Column(nullable = true)
	private String details;

	@ManyToOne
	@JoinColumn(name="customer_id", referencedColumnName="id", nullable=true)
	private CustomerEntity customer;
}
