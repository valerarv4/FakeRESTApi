package net.azurewebsites.fakerestapi.authors.update;

import net.azurewebsites.fakerestapi.authors.AbstractAuthorsRunner;
import net.azurewebsites.fakerestapi.models.author.Book;
import net.azurewebsites.fakerestapi.models.dtos.AuthorDto;
import net.azurewebsites.fakerestapi.models.dtos.ErrorResponseDto;
import net.azurewebsites.fakerestapi.utils.annotations.ApiVersion;
import net.azurewebsites.fakerestapi.utils.annotations.epics.AuthorEpic;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static net.azurewebsites.fakerestapi.models.repos.ErrorResponseDtoRepo.getErrorResponseDto;
import static net.azurewebsites.fakerestapi.utils.StreamUtils.randomItem;
import static org.apache.http.HttpStatus.SC_BAD_REQUEST;
import static org.assertj.core.api.Assertions.assertThat;

@AuthorEpic
public class UpdateAuthorEmptyDataNegativeTest extends AbstractAuthorsRunner {

    private Book book;
    private AuthorDto existingAuthorDto;
    private AuthorDto authorUpdateDto;
    private ErrorResponseDto expectedResponseError;

    @BeforeMethod
    public void initAuthorToUpdateAndResponseError() {
        book = booksPool.borrowBook();
        var author = book
                .getAuthors()
                .stream()
                .collect(randomItem());
        existingAuthorDto = mapAuthorToAuthorDto(book.getId(), author);
        authorUpdateDto = new AuthorDto();
        expectedResponseError = getErrorResponseDto("One or more validation errors occurred.", SC_BAD_REQUEST);
    }

    @ApiVersion(1)
    @Test(description = "Validates that updating an author with empty data returns a proper validation error")
    public void verifyUpdateAuthorEmptyDataNegativeTest() {
        var actualResponseError = authorClient
                .updateAuthorById(existingAuthorDto.getId(), authorUpdateDto)
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
