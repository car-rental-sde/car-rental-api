package it.unitn.carrentalapi.repository;

import it.unitn.carrentalapi.entity.CustomerEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<CustomerEntity, Long> {

    Optional<CustomerEntity> findByExternalId(Long externalId);
    CustomerEntity getByExternalId(Long externalId);

    @Query(value = "select c from CustomerEntity c " +
            "where c.name like :name " +
            "and c.surname like :surname ",

            countQuery = "select count(c.id) from CustomerEntity c " +
                    "where c.name like :name " +
                    "and c.surname like :surname ")
    Page<CustomerEntity> searchCustomers(@Param("name") String name,
                              @Param("surname") String surname,
                              Pageable pageable);
}
