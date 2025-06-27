package net.azurewebsites.fakerestapi.books;

import net.azurewebsites.fakerestapi.models.author.Book;
import net.azurewebsites.fakerestapi.models.dtos.BookDto;
import net.azurewebsites.fakerestapi.utils.annotations.ApiVersion;
import net.azurewebsites.fakerestapi.utils.annotations.epics.BookEpic;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.List;

import static org.apache.http.HttpStatus.SC_OK;
import static org.assertj.core.api.Assertions.assertThat;

@BookEpic
public class GetBooksTest extends AbstractBooksRunner {

    private List<Book> allAvailableBooks;
    private List<BookDto> expectedBooks;

    @BeforeMethod
    public void initBooks() {
        allAvailableBooks = booksPool.borrowAllAvailableBooks();
        expectedBooks = allAvailableBooks
                .stream()
                .map(this::mapBookToBookDto)
                .toList();
    }

    @ApiVersion(1)
    @Test(description = "Validates successful retrieval of all books")
    public void verifyGetBooksTest() {
        var books = bookClient
                .getBooks()
                .expectStatusCode(SC_OK)
                .getBodyAsList(BookDto.class);

        assertThat(books)
                .as("Response does not contain all expected books")
                .containsAll(expectedBooks);
    }

    @AfterMethod
    public void returnBooksToThePool() {
        booksPool.returnBooks(allAvailableBooks);
    }
}
