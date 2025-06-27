package net.azurewebsites.fakerestapi.books.update;

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
import static org.apache.http.HttpStatus.SC_BAD_REQUEST;
import static org.assertj.core.api.Assertions.assertThat;

@BookEpic
public class UpdateBookEmptyDataNegativeTest extends AbstractBooksRunner {

    private Book book;
    private BookDto existingBookDto;
    private BookDto bookUpdateDto;
    private ErrorResponseDto expectedResponseError;

    @BeforeMethod
    public void initBookToUpdateAndResponseError() {
        book = booksPool.borrowBook();
        existingBookDto = mapBookToBookDto(book);
        bookUpdateDto = new BookDto();
        expectedResponseError = getErrorResponseDto("One or more validation errors occurred.", SC_BAD_REQUEST);
    }

    @ApiVersion(1)
    @Test(description = "Validates error response when updating a book with empty request data")
    public void verifyUpdateBookEmptyDataNegativeTest() {
        var actualResponseError = bookClient
                .updateBookById(existingBookDto.getId(), bookUpdateDto)
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
