package net.azurewebsites.fakerestapi.config.books;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.Data;
import net.azurewebsites.fakerestapi.exceptions.TAFRuntimeException;
import net.azurewebsites.fakerestapi.models.author.Book;

import java.io.File;
import java.io.IOException;
import java.util.List;

import static com.fasterxml.jackson.databind.DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES;
import static java.util.Arrays.asList;
import static net.azurewebsites.fakerestapi.utils.StreamUtils.randomItem;

@Data
public class BooksConfig {

    private List<Book> books;

    public BooksConfig() {
        try {
            var mapper = new ObjectMapper()
                    .registerModule(new JavaTimeModule())
                    .configure(FAIL_ON_UNKNOWN_PROPERTIES, false);

            books = asList(mapper.readValue(
                    new File("src/main/resources/books/books.json"),
                    Book[].class
            ));
        } catch (IOException e) {
            throw new TAFRuntimeException("Can not read file with books", e);
        }
    }

    public Book getRandomBook() {
        return books
                .stream()
                .collect(randomItem());
    }
}
