package net.azurewebsites.fakerestapi.authors.create;

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
import static org.apache.http.HttpStatus.SC_CONFLICT;
import static org.assertj.core.api.Assertions.assertThat;

@AuthorEpic
public class CreateAuthorWithExistedIdNegativeTest extends AbstractAuthorsRunner {

    private Book book;
    private AuthorDto authorDto;
    private ErrorResponseDto expectedResponseError;

    @BeforeMethod
    public void initExitedBookAndResponseError() {
        book = booksPool.borrowBook();
        var author = book
                .getAuthors()
                .stream()
                .collect(randomItem());
        authorDto = mapAuthorToAuthorDto(book.getId(), author);
        expectedResponseError = getErrorResponseDto("Author with the given id already exists", SC_CONFLICT);
    }

    @ApiVersion(1)
    @Test(description = "Validates error response when attempting to create a author with an id that already exists")
    public void verifyCreateAuthorWithExistedIdNegativeTest() {
        var actualResponseError = authorClient
                .createAuthor(authorDto)
                .expectStatusCode(expectedResponseError.getStatus())
                .getBodyAs(ErrorResponseDto.class);

        assertThat(actualResponseError)
                .as("Actual error response does not match expected when trying to create a author with an existing id")
                .isEqualTo(expectedResponseError);
    }

    @AfterMethod
    public void returnBookToThePool() {
        booksPool.returnBook(book);
    }
}
