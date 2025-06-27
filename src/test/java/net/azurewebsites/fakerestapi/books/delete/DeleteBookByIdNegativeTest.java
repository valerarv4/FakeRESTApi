package net.azurewebsites.fakerestapi.books.delete;

import net.azurewebsites.fakerestapi.books.AbstractBooksRunner;
import net.azurewebsites.fakerestapi.models.dtos.BookDto;
import net.azurewebsites.fakerestapi.utils.annotations.ApiVersion;
import net.azurewebsites.fakerestapi.utils.annotations.epics.BookEpic;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static net.azurewebsites.fakerestapi.utils.StreamUtils.collectNonExistingInt;
import static org.apache.http.HttpStatus.SC_NOT_FOUND;
import static org.apache.http.HttpStatus.SC_OK;

@BookEpic
public class DeleteBookByIdNegativeTest extends AbstractBooksRunner {

    private int bookId;

    @BeforeMethod
    public void initBook() {
        bookId = bookClient
                .getBooks()
                .expectStatusCode(SC_OK)
                .getBodyAsList(BookDto.class)
                .stream()
                .map(BookDto::getId)
                .collect(collectNonExistingInt());
    }

    @ApiVersion(1)
    @Test(description = "Validates error response when attempting to delete a non-existent book by id")
    public void verifyDeleteBookByIdNegativeTest() {
        bookClient
                .deleteBookById(bookId)
                .expectStatusCode(SC_NOT_FOUND);
    }
}
