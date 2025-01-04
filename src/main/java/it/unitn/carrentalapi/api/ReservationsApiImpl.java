package it.unitn.carrentalapi.api;

import it.unitn.carrentalapi.entity.ReservationEntity;
import it.unitn.carrentalapi.mapper.EntityToModelMappers;
import it.unitn.carrentalapi.openapi.api.ReservationsApiDelegate;
import it.unitn.carrentalapi.openapi.model.*;
import it.unitn.carrentalapi.service.ReservationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Optional;

@Slf4j
@Component
@RequiredArgsConstructor
public class ReservationsApiImpl implements ReservationsApiDelegate {

    private final ReservationService reservationService;

    private final EntityToModelMappers mappers;

    @Override
    public ResponseEntity<ReservationsPaginationResponseModel> searchReservations(Long customerExternalId,
                                                                                  Long carId,
                                                                                  LocalDate startDate,
                                                                                  LocalDate endDate,
                                                                                  String startPlace,
                                                                                  String endPlace,
                                                                                  ReservationsSortColumn sortBy,
                                                                                  SortDirection sortDirection,
                                                                                  Integer page,
                                                                                  Integer size) {

        log.debug("Searching reservations with customerExternalId: [{}], carId: [{}], startDate: [{}], endDate: [{}], startPlace: [{}], " +
                        "endPlace: [{}], sortBy: [{}], sortDirection: [{}], page: [{}], size: [{}]",
                customerExternalId, carId, startDate, endDate, startPlace, endPlace, sortBy, sortDirection, page, size);

        Page<ReservationEntity> reservations = reservationService.searchReservations(customerExternalId, carId, startDate, endDate, startPlace, endPlace, sortBy, sortDirection, page, size);

        ReservationsPaginationResponseModel response = new ReservationsPaginationResponseModel();
        response.setReservations(reservations.stream().map(mappers::reservationToReservationModel).toList());
        response.setPageNumber(reservations.getNumber() + 1);
        response.setPageSize(reservations.getSize());
        response.setTotalRecords(reservations.getTotalElements());

        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<ReservationModel> getReservation(Long id) {
        log.debug("Getting reservation with id: [{}]", id);

        Optional<ReservationEntity> optionalReservation = reservationService.getReservation(id);

        return optionalReservation.map(reservation -> ResponseEntity.ok(mappers.reservationToReservationModel(reservation)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Override
    public ResponseEntity<ReservationModel> addReservation(ReservationRequestModel reservationRequest) {
        log.debug("Adding reservation with request: [{}]", reservationRequest);

        Optional<ReservationEntity> reservationOptional = reservationService.addReservation(reservationRequest);
        return reservationOptional.map(reservationEntity -> ResponseEntity.ok(mappers.reservationToReservationModel(reservationEntity)))
                .orElseGet(() -> ResponseEntity.badRequest().build()); // TODO: Other code when car is not available? Like in pc-profiles?

    }

    @Override
    public ResponseEntity<Void> deleteReservation(Long id) {
        log.debug("Deleting reservation with id: [{}]", id);

        reservationService.deleteReservation(id);
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<ReservationModel> updateReservation(Long id, ReservationRequestModel reservationRequest) {
        log.debug("Updating reservation with id: [{}], request: [{}]", id, reservationRequest);

        return ResponseEntity.ok(mappers.reservationToReservationModel(reservationService.updateReservation(id, reservationRequest)));
    }
}
