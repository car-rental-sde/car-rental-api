package it.unitn.carrentalapi.graphql.simpleexample.model;

import lombok.Data;

@Data
public class AddBookInput {
    private String name;
    private int pageCount;
    private Long authorId;
    private String content;
}
