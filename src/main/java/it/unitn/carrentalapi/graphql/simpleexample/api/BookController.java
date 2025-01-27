package it.unitn.carrentalapi.graphql.simpleexample.api;

import it.unitn.carrentalapi.graphql.simpleexample.model.Author;
import it.unitn.carrentalapi.graphql.simpleexample.model.Book;
import it.unitn.carrentalapi.graphql.simpleexample.entity.AuthorEntity;
import it.unitn.carrentalapi.graphql.simpleexample.entity.BookEntity;
import it.unitn.carrentalapi.graphql.simpleexample.model.AuthorInput;
import it.unitn.carrentalapi.graphql.simpleexample.model.AddBookInput;
import it.unitn.carrentalapi.graphql.simpleexample.repository.AuthorRepository;
import it.unitn.carrentalapi.graphql.simpleexample.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Controller
@RequiredArgsConstructor
public class BookController {

    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;

    @QueryMapping
    public Optional<Book> book(@Argument long id) {
        log.debug("Fetching book with id: [{}]", id);
        return bookRepository.findById(id).map(this::toBook);
    }

    @QueryMapping
    public List<Book> books() {
        log.debug("Fetching all books");
        return bookRepository.findAll().stream().map(this::toBook).collect(Collectors.toList());
    }

    @SchemaMapping
    public Author author(Book book) {
        log.warn("Very slow/expensive operation");
        return authorRepository.findById(book.authorId()).map(this::toAuthor).orElse(null);
    }

    @QueryMapping
    public Optional<Author> author(@Argument long id) {
        log.debug("Fetching author with id: [{}]", id);
        return authorRepository.findById(id).map(this::toAuthor);
    }

    @QueryMapping
    public List<Author> authors() {
        log.debug("Fetching all authors");
        return authorRepository.findAll().stream().map(this::toAuthor).collect(Collectors.toList());
    }

    @MutationMapping
    public Book addBook(@Argument AddBookInput input) {
        log.debug("Adding book with name: [{}]", input.getName());
        BookEntity book = new BookEntity();
        book.setName(input.getName());
        book.setPageCount(input.getPageCount());
        book.setAuthorId(input.getAuthorId());
        book.setContent(input.getContent());
        BookEntity savedBook = bookRepository.save(book);
        return toBook(savedBook);
    }

    @MutationMapping
    public Author addAuthor(@Argument AuthorInput input) {
        log.debug("Adding author with first name: [{}]", input.getFirstName());
        AuthorEntity author = new AuthorEntity();
        author.setFirstName(input.getFirstName());
        author.setLastName(input.getLastName());
        AuthorEntity savedAuthor = authorRepository.save(author);
        return toAuthor(savedAuthor);
    }

    @MutationMapping
    public Author updateAuthor(@Argument long id, @Argument AuthorInput input) {
        log.debug("Updating author with id: [{}]", id);
        Optional<AuthorEntity> existingAuthor = authorRepository.findById(id);
        if (existingAuthor.isPresent()) {
            AuthorEntity author = existingAuthor.get();
            if (input.getFirstName() != null) {
                author.setFirstName(input.getFirstName());
            }
            if (input.getLastName() != null) {
                author.setLastName(input.getLastName());
            }
            AuthorEntity updatedAuthor = authorRepository.save(author);
            return toAuthor(updatedAuthor);
        } else {
            throw new IllegalArgumentException("Author with id " + id + " not found.");
        }
    }

    private Book toBook(BookEntity bookEntity) {
        return new Book(
                String.valueOf(bookEntity.getId()),
                bookEntity.getName(),
                bookEntity.getPageCount(),
                bookEntity.getAuthorId(),
                bookEntity.getContent()
        );
    }

    private Author toAuthor(AuthorEntity authorEntity) {
        return new Author(
                String.valueOf(authorEntity.getId()),
                authorEntity.getFirstName(),
                authorEntity.getLastName()
        );
    }
}
