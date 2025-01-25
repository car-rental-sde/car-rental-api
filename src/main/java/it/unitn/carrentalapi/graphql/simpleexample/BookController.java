package it.unitn.carrentalapi.graphql.simpleexample;

import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.Optional;

@Controller
public class BookController {
    @QueryMapping
    public Optional<Book> bookById(@Argument String id) {
        return Book.getById(id);
    }

    @QueryMapping
    public List<Book> getBooks() {
        return Book.getBooks();
    }

    @SchemaMapping
    public Author author(Book book) {
        return Author.getById(book.authorId());
    }
}
