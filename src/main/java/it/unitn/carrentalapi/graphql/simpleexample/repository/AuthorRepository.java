package it.unitn.carrentalapi.graphql.simpleexample.repository;

import it.unitn.carrentalapi.graphql.simpleexample.entity.AuthorEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthorRepository extends JpaRepository<AuthorEntity, Long> {
}
