package it.unitn.carrentalapi.api;

import it.unitn.carrentalapi.entity.ReservationEntity;
import it.unitn.carrentalapi.facade.CurrencyExchangeFacade;
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
import java.util.Currency;
import java.util.Optional;

@Slf4j
@Component
@RequiredArgsConstructor
public class ReservationsApiImpl implements ReservationsApiDelegate {

    private final ReservationService reservationService;

    private final EntityToModelMappers mappers;
    private final CurrencyExchangeFacade currencyExchangeFacade;

    @Override
    public ResponseEntity<ReservationsPaginationResponseModel> searchReservations(Long customerExternalId,
                                                                                  Long carId,
                                                                                  LocalDate startDate,
                                                                                  LocalDate endDate,
                                                                                  ReservationsSortColumn sortBy,
                                                                                  SortDirection sortDirection,
                                                                                  Integer page,
                                                                                  Integer size) {

        log.debug("Searching reservations with customerExternalId: [{}], carId: [{}], startDate: [{}], endDate: [{}], " +
                        "sortBy: [{}], sortDirection: [{}], page: [{}], size: [{}]",
                customerExternalId, carId, startDate, endDate, sortBy, sortDirection, page, size);

        Page<ReservationEntity> reservations = reservationService.searchReservations(customerExternalId, carId, startDate, endDate, sortBy, sortDirection, page, size);

        ReservationsPaginationResponseModel response = new ReservationsPaginationResponseModel();
        response.setReservations(reservations.stream().map(mappers::reservationToReservationModel).toList());
        response.setPageNumber(reservations.getNumber() + 1);
        response.setPageSize(reservations.getSize());
        response.setTotalRecords(reservations.getTotalElements());

        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<ReservationModel> getReservation(Long id, String currency) {
        log.debug("Getting reservation with id: [{}]", id);

        Optional<ReservationEntity> reservationOptional = reservationService.getReservation(id);

        if (reservationOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        ReservationModel reservation = mappers.reservationToReservationModel(reservationOptional.get());

        if (currency != null && !currency.isBlank() && !currency.equals("EUR")) {
            try {
                Currency.getInstance(currency);

                Long dayPriceEuro = reservation.getCost();
                Optional<Long> convertedOptional = currencyExchangeFacade.convert(dayPriceEuro, "EUR", currency);

                if (convertedOptional.isEmpty()) {
                    log.warn("Failed to convert day price from EUR to [{}], using default currency EUR", currency);
                    return ResponseEntity.ok(reservation);
                }

                reservation.setCurrency(currency);
                reservation.setCost(convertedOptional.get());
            } catch (Exception e) {
                log.warn("Currency [{}] is not valid, using default currency EUR", currency);
            }
        }

        return ResponseEntity.ok(reservation);
    }

    @Override
    public ResponseEntity<ReservationModel> addReservation(ReservationRequestModel reservationRequest) {
        log.debug("Adding reservation with request: [{}]", reservationRequest);

        Optional<ReservationEntity> reservationOptional = reservationService.addReservation(reservationRequest);

        if (reservationOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        ReservationModel reservation = mappers.reservationToReservationModel(reservationOptional.get());

        String currency = reservationRequest.getCurrency();
        if (currency != null && !currency.isBlank() && !currency.equals("EUR")) {
            try {
                Currency.getInstance(currency);

                Long costInEuro = reservation.getCost();
                Optional<Long> convertedOptional = currencyExchangeFacade.convert(costInEuro, "EUR", currency);

                if (convertedOptional.isEmpty()) {
                    log.warn("Failed to convert cost from EUR to [{}], using default currency EUR", currency);
                    return ResponseEntity.ok(reservation);
                }

                reservation.setCurrency(currency);
                reservation.setCost(convertedOptional.get());
            } catch (Exception e) {
                log.warn("Currency [{}] is not valid, using default currency EUR", currency);
            }
        }

        return ResponseEntity.ok(reservation);
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
