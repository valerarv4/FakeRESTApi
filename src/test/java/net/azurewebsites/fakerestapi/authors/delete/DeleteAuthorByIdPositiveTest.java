package net.azurewebsites.fakerestapi.authors.delete;

import net.azurewebsites.fakerestapi.authors.AbstractAuthorsRunner;
import net.azurewebsites.fakerestapi.models.dtos.AuthorDto;
import net.azurewebsites.fakerestapi.utils.annotations.ApiVersion;
import net.azurewebsites.fakerestapi.utils.annotations.epics.AuthorEpic;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static net.azurewebsites.fakerestapi.models.repos.AuthorDtoRepo.getRandomAuthorDto;
import static org.apache.http.HttpStatus.SC_NOT_FOUND;
import static org.apache.http.HttpStatus.SC_OK;

@AuthorEpic
public class DeleteAuthorByIdPositiveTest extends AbstractAuthorsRunner {

    private AuthorDto authorToCreateDto;

    @BeforeMethod
    public void initAuthor() {
        var bookId = booksConfig
                .getRandomBook()
                .getId();
        authorToCreateDto = getRandomAuthorDto(bookId);

        authorClient
                .createAuthor(authorToCreateDto)
                .expectStatusCode(SC_OK);

        authorClient
                .getAuthorById(authorToCreateDto.getId())
                .expectStatusCode(SC_OK);
    }

    @ApiVersion(1)
    @Test(description = "Validates successful deletion of a author by id")
    public void verifyDeleteAuthorByIdPositiveTest() {
        authorClient
                .deleteAuthorById(authorToCreateDto.getId())
                .expectStatusCode(SC_OK);

        authorClient
                .getAuthorById(authorToCreateDto.getId())
                .expectStatusCode(SC_NOT_FOUND);
    }
}
