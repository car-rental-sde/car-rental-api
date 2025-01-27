package it.unitn.carrentalapi.graphql.simpleexample.model;

import lombok.Data;

@Data
public class AuthorInput {
    private String firstName;
    private String lastName;
}
