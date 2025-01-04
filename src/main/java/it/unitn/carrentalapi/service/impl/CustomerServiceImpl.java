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

@Slf4j
@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;

    @Override
    public Page<CustomerEntity> searchCustomers(String name,
                                                String surname,
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

        Page<CustomerEntity> customerPage = customerRepository.searchCustomers(name, surname, pageRequest);

        return customerPage;
    }

    private String addSqlWildcards(String arg) {
        return StringUtils.defaultIfBlank(arg, "") + "%";
    }
}
