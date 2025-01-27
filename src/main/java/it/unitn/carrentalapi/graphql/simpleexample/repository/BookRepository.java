package it.unitn.carrentalapi.graphql.simpleexample.repository;

import it.unitn.carrentalapi.graphql.simpleexample.entity.BookEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends JpaRepository<BookEntity, Long> {
}
