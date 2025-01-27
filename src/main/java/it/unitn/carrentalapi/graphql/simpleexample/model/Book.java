package it.unitn.carrentalapi.graphql.simpleexample.model;

public record Book (String id, String name, int pageCount, Long authorId, String content) {
}
