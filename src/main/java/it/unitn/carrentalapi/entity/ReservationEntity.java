package it.unitn.carrentalapi.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Entity
@Data
@Table(name = "reservation")
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
	private String beginPosition;

	@Column(nullable = false)
	private String endPosition;

	@Column(nullable = false)
	private Boolean isMaintenance;

	@Column(nullable = false)
	private Long cost;

	@ManyToOne
	@JoinColumn(name="customer_id", referencedColumnName="id")
	private CustomerEntity customer;
}
