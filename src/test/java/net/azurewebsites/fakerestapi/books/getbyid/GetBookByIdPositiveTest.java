package net.azurewebsites.fakerestapi.books.getbyid;

import net.azurewebsites.fakerestapi.books.AbstractBooksRunner;
import net.azurewebsites.fakerestapi.models.author.Book;
import net.azurewebsites.fakerestapi.models.dtos.BookDto;
import net.azurewebsites.fakerestapi.utils.annotations.ApiVersion;
import net.azurewebsites.fakerestapi.utils.annotations.epics.BookEpic;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.apache.http.HttpStatus.SC_OK;
import static org.assertj.core.api.Assertions.assertThat;

@BookEpic
public class GetBookByIdPositiveTest extends AbstractBooksRunner {

    private Book book;

    @BeforeMethod
    public void initBook() {
        book = booksConfig.getRandomBook();
    }

    @ApiVersion(1)
    @Test(description = "Validates successful retrieval of a book by id")
    public void verifyGetBookByIdPositiveTest() {
        var books = bookClient
                .getBookById(book.getId())
                .expectStatusCode(SC_OK)
                .getBodyAs(BookDto.class);

        var expectedBook = mapBookToBookDto(book);
        assertThat(books)
                .as("Book with id [%s] from API does not match expected".formatted(book.getId()))
                .isEqualTo(expectedBook);
    }
}
