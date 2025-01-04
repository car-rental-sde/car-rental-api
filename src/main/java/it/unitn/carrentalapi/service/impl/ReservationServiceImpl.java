package it.unitn.carrentalapi.service.impl;

import it.unitn.carrentalapi.entity.CarEntity;
import it.unitn.carrentalapi.entity.CustomerEntity;
import it.unitn.carrentalapi.entity.ReservationEntity;
import it.unitn.carrentalapi.mapper.EntityToModelMappers;
import it.unitn.carrentalapi.openapi.model.ReservationRequestModel;
import it.unitn.carrentalapi.openapi.model.ReservationsSortColumn;
import it.unitn.carrentalapi.openapi.model.SortDirection;
import it.unitn.carrentalapi.repository.CarRepository;
import it.unitn.carrentalapi.repository.CustomerRepository;
import it.unitn.carrentalapi.repository.ReservationRepository;
import it.unitn.carrentalapi.repository.UserRepository;
import it.unitn.carrentalapi.service.ReservationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
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
    private final UserRepository userRepository; // TODO: ??
    private final EntityToModelMappers mappers;

    @Override
    public Page<ReservationEntity> searchReservations(Long carId,
                                                      LocalDate startDate,
                                                      LocalDate endDate,
                                                      String startPlace,
                                                      String endPlace,
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
        startPlace = addSqlWildcards(startPlace);
        endPlace = addSqlWildcards(endPlace);

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

        return reservationRepository.searchReservations(carId, startDate, endDate, startPlace, endPlace, pageRequest);
    }

    @Override
    public Optional<ReservationEntity> addReservation(ReservationRequestModel reservationRequest) {

        ReservationEntity reservation = mappers.putReservationModelToReservation(reservationRequest);

        CarEntity car = carRepository.getCarIfNoReservation(reservationRequest.getCarId(), reservationRequest.getBeginDate(), reservationRequest.getEndDate());
        if (car == null) {
            return Optional.empty();
        }
        reservation.setCar(car);

        if (reservationRequest.getCustomer() != null) {
            Optional<CustomerEntity> customer = customerRepository.findByExternalId(reservationRequest.getCustomer().getExternalId());

            if (customer.isPresent()) {
                reservation.setCustomer(customerRepository.getByExternalId(reservationRequest.getCustomer().getExternalId()));
            } else {
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

        ReservationEntity reservation = reservationRepository.getById(id);
        reservation.setCar(carRepository.getById(reservationRequest.getCarId()));

        reservation.setBeginDate(reservationRequest.getBeginDate());
        reservation.setEndDate(reservationRequest.getEndDate());
        reservation.setBeginPlace(reservationRequest.getBeginPlace());
        reservation.setEndPlace(reservationRequest.getEndPlace());
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
                reservation.setCustomer(customerRepository.getById(reservationRequest.getCustomer().getExternalId()));
            } else {
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

    private String addSqlWildcards(String arg) {
        return StringUtils.defaultIfBlank(arg, "") + "%";
    }
}
