package it.unitn.carrentalapi.service.impl;

import it.unitn.carrentalapi.entity.CarEntity;
import it.unitn.carrentalapi.entity.CustomerEntity;
import it.unitn.carrentalapi.entity.ReservationEntity;
import it.unitn.carrentalapi.facade.MapApiFacade;
import it.unitn.carrentalapi.mapper.EntityToModelMappers;
import it.unitn.carrentalapi.openapi.model.ReservationRequestModel;
import it.unitn.carrentalapi.openapi.model.ReservationsSortColumn;
import it.unitn.carrentalapi.openapi.model.SortDirection;
import it.unitn.carrentalapi.repository.CarRepository;
import it.unitn.carrentalapi.repository.CustomerRepository;
import it.unitn.carrentalapi.repository.ReservationRepository;
import it.unitn.carrentalapi.service.ReservationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class ReservationServiceImpl implements ReservationService {

    private final ReservationRepository reservationRepository;
    private final CarRepository carRepository;
    private final CustomerRepository customerRepository;
    private final EntityToModelMappers mappers;
    private final MapApiFacade mapApiFacade;

    private static final String TRENTO_PARKING_POSITION = "46.06706818314613,11.150432144111885";

    @Override
    public Page<ReservationEntity> searchReservations(Long customerExternalId,
                                                      Long carId,
                                                      LocalDate startDate,
                                                      LocalDate endDate,
                                                      ReservationsSortColumn sortBy,
                                                      SortDirection sortDirection,
                                                      Integer page,
                                                      Integer size) {


        if (startDate == null) {
            startDate = LocalDate.of(1900, 1, 1);
        }
        if (endDate == null) {
            endDate = LocalDate.of(2500, 1, 1);
        }

        if (sortBy == null) {
            sortBy = ReservationsSortColumn.ID;
        }
        if (sortDirection == null) {
            sortDirection = SortDirection.ASC;
        }

        Pageable pageRequest;
        Sort.Direction direction = SortDirection.ASC.equals(sortDirection) ?
                Sort.Direction.ASC : Sort.Direction.DESC;

        pageRequest = switch (sortBy) {
            case BRAND -> PageRequest.of(page - 1, size, direction, "car.model.brand.name");
            case MODEL -> PageRequest.of(page - 1, size, direction, "car.model.name");
            case CAR_TYPE -> PageRequest.of(page - 1, size, direction, "car.model.carType.name");
            default -> PageRequest.of(page - 1, size, direction, sortBy.getValue());
        };

        return reservationRepository.searchReservations(customerExternalId, carId, startDate, endDate, pageRequest);
    }

    @Override
    public Optional<ReservationEntity> addReservation(ReservationRequestModel reservationRequest) {

        ReservationEntity reservation = mappers.putReservationModelToReservation(reservationRequest);

        CarEntity car = carRepository.getCarIfNoReservation(reservationRequest.getCarId(), reservationRequest.getBeginDate(), reservationRequest.getEndDate());
        if (car == null) {
            log.debug("Car is not available");
            return Optional.empty();
        }
        reservation.setCar(car);

        long costOfCar = car.getDayPriceEuro() * reservation.getBeginDate().until(reservation.getEndDate()).getDays();

        long costOfDistance = 0L;
        Optional<Integer> pickupDistance = mapApiFacade.getDistanceInMeters(TRENTO_PARKING_POSITION, reservation.getBeginPosition());
        if (pickupDistance.isPresent()) {
            costOfDistance += pickupDistance.get() / 1000 * 5;
        }
        Optional<Integer> returnDistance = mapApiFacade.getDistanceInMeters(reservation.getEndPosition(), TRENTO_PARKING_POSITION);
        if (returnDistance.isPresent()) {
            costOfDistance += returnDistance.get() / 1000 * 5;
        }

        reservation.setCost(costOfCar + costOfDistance);

        if (reservationRequest.getCalculateOnly()) {
            return Optional.of(reservation);
        }

        if (reservationRequest.getCustomer() != null) {
            Optional<CustomerEntity> customer = customerRepository.findByExternalId(reservationRequest.getCustomer().getExternalId());

            if (customer.isPresent()) {
                reservation.setCustomer(customerRepository.getByExternalId(reservationRequest.getCustomer().getExternalId()));
            } else {
                log.debug("Customer not found, for new reservation creating new customer [{}]", reservationRequest.getCustomer());
                createAndSaveNewCustomer(reservationRequest, reservation);
            }
        }

        return Optional.of(reservationRepository.save(reservation));
    }

    @Override
    public Optional<ReservationEntity> getReservation(Long id) {
        return reservationRepository.findById(id);
    }

    @Override
    public void deleteReservation(Long id) {
        reservationRepository.deleteById(id);
    }

    @Override
    public ReservationEntity updateReservation(Long id, ReservationRequestModel reservationRequest) {

        ReservationEntity reservation = reservationRepository.getReferenceById(id);
        reservation.setCar(carRepository.getReferenceById(reservationRequest.getCarId()));

        reservation.setBeginDate(reservationRequest.getBeginDate());
        reservation.setEndDate(reservationRequest.getEndDate());
        reservation.setBeginPosition(reservationRequest.getBeginPosition());
        reservation.setEndPosition(reservationRequest.getEndPosition());
        reservation.setIsMaintenance(reservationRequest.getIsMaintenance());

        // add or update customer
        if (reservationRequest.getCustomer() != null) {
            Optional<CustomerEntity> customerOptional = customerRepository.findByExternalId(reservationRequest.getCustomer().getExternalId());

            if (customerOptional.isPresent()) {
                CustomerEntity customer = customerOptional.get();
                customer.setExternalId(reservationRequest.getCustomer().getExternalId());
                customer.setName(reservationRequest.getCustomer().getName());
                customer.setSurname(reservationRequest.getCustomer().getSurname());

                customerRepository.save(customer);
                reservation.setCustomer(customerRepository.getReferenceById(reservationRequest.getCustomer().getExternalId()));
            } else {
                log.debug("Customer not found, for reservation [{}] creating new customer [{}]", id, reservationRequest.getCustomer());
                createAndSaveNewCustomer(reservationRequest, reservation);
            }
        }

        return reservationRepository.save(reservation);
    }

    private void createAndSaveNewCustomer(ReservationRequestModel reservationRequest, ReservationEntity reservation) {
        CustomerEntity newCustomer = new CustomerEntity();
        newCustomer.setName(reservationRequest.getCustomer().getName());
        newCustomer.setSurname(reservationRequest.getCustomer().getSurname());
        newCustomer.setExternalId(reservationRequest.getCustomer().getExternalId());
        customerRepository.save(newCustomer);

        reservation.setCustomer(customerRepository.getByExternalId(newCustomer.getExternalId()));
    }
}
