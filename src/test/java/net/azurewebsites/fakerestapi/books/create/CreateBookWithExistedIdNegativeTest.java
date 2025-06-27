package net.azurewebsites.fakerestapi.books.create;

import net.azurewebsites.fakerestapi.books.AbstractBooksRunner;
import net.azurewebsites.fakerestapi.models.author.Book;
import net.azurewebsites.fakerestapi.models.dtos.BookDto;
import net.azurewebsites.fakerestapi.models.dtos.ErrorResponseDto;
import net.azurewebsites.fakerestapi.utils.annotations.ApiVersion;
import net.azurewebsites.fakerestapi.utils.annotations.epics.BookEpic;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static net.azurewebsites.fakerestapi.models.repos.ErrorResponseDtoRepo.getErrorResponseDto;
import static org.apache.http.HttpStatus.SC_CONFLICT;
import static org.assertj.core.api.Assertions.assertThat;

@BookEpic
public class CreateBookWithExistedIdNegativeTest extends AbstractBooksRunner {

    private Book book;
    private BookDto bookDto;
    private ErrorResponseDto expectedResponseError;

    @BeforeMethod
    public void initExitedBookAndResponseError() {
        book = booksPool.borrowBook();
        bookDto = mapBookToBookDto(book);
        expectedResponseError = getErrorResponseDto("Book with the given id already exists", SC_CONFLICT);
    }


    @ApiVersion(1)
    @Test(description = "Validates error response when attempting to create a book with an id that already exists")
    public void verifyCreateBookWithExistedIdNegativeTest() {
        var actualResponseError = bookClient
                .createBook(bookDto)
                .expectStatusCode(expectedResponseError.getStatus())
                .getBodyAs(ErrorResponseDto.class);

        assertThat(actualResponseError)
                .as("Actual error response does not match expected when trying to create a book with an existing id")
                .isEqualTo(expectedResponseError);
    }

    @AfterMethod
    public void returnBookToThePool() {
        booksPool.returnBook(book);
    }
}
