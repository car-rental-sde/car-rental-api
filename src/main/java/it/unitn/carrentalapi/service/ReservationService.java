package it.unitn.carrentalapi.service;

import it.unitn.carrentalapi.entity.ReservationEntity;
import it.unitn.carrentalapi.openapi.model.ReservationRequestModel;
import it.unitn.carrentalapi.openapi.model.ReservationsSortColumn;
import it.unitn.carrentalapi.openapi.model.SortDirection;
import org.springframework.data.domain.Page;

import java.time.LocalDate;
import java.util.Optional;

public interface ReservationService {
    Page<ReservationEntity> searchReservations(Long customerExternalId,
                                               Long carId,
                                               LocalDate startDate,
                                               LocalDate endDate,
                                               ReservationsSortColumn sortBy,
                                               SortDirection sortDirection,
                                               Integer page,
                                               Integer size);
    Optional<ReservationEntity> addReservation(ReservationRequestModel putReservationDto);
    Optional<ReservationEntity> getReservation(Long id);
    void deleteReservation(Long id);
    ReservationEntity updateReservation(Long id, ReservationRequestModel putReservationDto);
}
