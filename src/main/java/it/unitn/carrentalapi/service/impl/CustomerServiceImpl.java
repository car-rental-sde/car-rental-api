package it.unitn.carrentalapi.service.impl;

import it.unitn.carrentalapi.entity.CustomerEntity;
import it.unitn.carrentalapi.openapi.model.CustomersSortColumn;
import it.unitn.carrentalapi.openapi.model.SortDirection;
import it.unitn.carrentalapi.repository.CustomerRepository;
import it.unitn.carrentalapi.service.CustomerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;

    @Override
    public Page<CustomerEntity> searchCustomers(String name,
                                                String surname,
                                                Boolean isBlocked,
                                                CustomersSortColumn sortBy,
                                                SortDirection sortDirection,
                                                Integer page,
                                                Integer size) {

        name = addSqlWildcards(name);
        surname = addSqlWildcards(surname);

        Pageable pageRequest;
        Sort.Direction direction = SortDirection.DESC.equals(sortDirection) ?
                Sort.Direction.DESC : Sort.Direction.ASC;

        if (sortBy == null) {
            sortBy = CustomersSortColumn.ID;
        }

        pageRequest = switch (sortBy) {
            default -> PageRequest.of(page - 1, size, direction, sortBy.getValue());
        };

        Page<CustomerEntity> customerPage;
        if (isBlocked == null) {
            customerPage = customerRepository.searchCustomers(name, surname, pageRequest);
        } else {
            customerPage = customerRepository.searchCustomersIsBlocked(name, surname, isBlocked, pageRequest);
        }

        return customerPage;
    }

    @Override
    public void blockCustomer(Long customerId) {
        Optional<CustomerEntity> customer = customerRepository.findById(customerId);

        if (customer.isPresent()) {
            customer.get().setIsBlocked(true);
            customerRepository.save(customer.get());
        }
    }

    @Override
    public void unblockCustomer(Long customerId) {
        Optional<CustomerEntity> customer = customerRepository.findById(customerId);

        if (customer.isPresent()) {
            customer.get().setIsBlocked(false);
            customerRepository.save(customer.get());
        }
    }

    private String addSqlWildcards(String arg) {
        return StringUtils.defaultIfBlank(arg, "") + "%";
    }
}
