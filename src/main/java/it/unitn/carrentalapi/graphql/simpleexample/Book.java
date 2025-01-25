package it.unitn.carrentalapi.graphql.simpleexample;

import lombok.Getter;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public record Book (String id, String name, int pageCount, String authorId, String content) {
    private static final String loremIpsum = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore etdolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquipex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eufugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.";

    private static final List<Book> books = Arrays.asList(
            new Book("book-1", "Effective Java", 416, "author-1", loremIpsum),
            new Book("book-2", "Hitchhiker's Guide to the Galaxy", 208, "author-2", loremIpsum)
            // new Book("book-3", "Down Under", 436, "author-3", loremIpsum)
    );

    public static Optional<Book> getById(String id) {
        return books.stream()
                .filter(book -> book.id().equals(id))
                .findFirst();
    }

    public static List<Book> getBooks() {
        return books;
    }
}
