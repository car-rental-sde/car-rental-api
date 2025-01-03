package it.unitn.carrentalapi.service;

import it.unitn.carrentalapi.entity.CustomerEntity;
import it.unitn.carrentalapi.openapi.model.CustomersSortColumn;
import it.unitn.carrentalapi.openapi.model.SortDirection;
import org.springframework.data.domain.Page;

public interface CustomerService {
    Page<CustomerEntity> searchCustomers(String name,
                                         String surname,
                                         Boolean isBlocked,
                                         CustomersSortColumn sortBy,
                                         SortDirection sortDirection,
                                         Integer page,
                                         Integer size);
    void blockCustomer(Long customerId);
    void unblockCustomer(Long customerId);
}
