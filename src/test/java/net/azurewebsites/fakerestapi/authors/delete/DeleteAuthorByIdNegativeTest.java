package net.azurewebsites.fakerestapi.authors.delete;

import net.azurewebsites.fakerestapi.authors.AbstractAuthorsRunner;
import net.azurewebsites.fakerestapi.models.dtos.AuthorDto;
import net.azurewebsites.fakerestapi.utils.annotations.ApiVersion;
import net.azurewebsites.fakerestapi.utils.annotations.epics.AuthorEpic;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static net.azurewebsites.fakerestapi.utils.StreamUtils.collectNonExistingInt;
import static org.apache.http.HttpStatus.SC_NOT_FOUND;
import static org.apache.http.HttpStatus.SC_OK;

@AuthorEpic
public class DeleteAuthorByIdNegativeTest extends AbstractAuthorsRunner {

    private int authorId;

    @BeforeMethod
    public void initBook() {
        authorId = authorClient
                .getAuthors()
                .expectStatusCode(SC_OK)
                .getBodyAsList(AuthorDto.class)
                .stream()
                .map(AuthorDto::getId)
                .collect(collectNonExistingInt());
    }

    @ApiVersion(1)
    @Test(description = "Validates error response when attempting to delete a non-existent author by id")
    public void verifyDeleteAuthorByIdNegativeTest() {
        authorClient
                .deleteAuthorById(authorId)
                .expectStatusCode(SC_NOT_FOUND);
    }
}
