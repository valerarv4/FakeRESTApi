package net.azurewebsites.fakerestapi.authors.getbyid;

import net.azurewebsites.fakerestapi.authors.AbstractAuthorsRunner;
import net.azurewebsites.fakerestapi.models.dtos.AuthorDto;
import net.azurewebsites.fakerestapi.utils.annotations.ApiVersion;
import net.azurewebsites.fakerestapi.utils.annotations.epics.AuthorEpic;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static net.azurewebsites.fakerestapi.utils.StreamUtils.randomItem;
import static org.apache.http.HttpStatus.SC_OK;
import static org.assertj.core.api.Assertions.assertThat;

@AuthorEpic
public class GetAuthorByIdPositiveTest extends AbstractAuthorsRunner {

    private AuthorDto authorDto;

    @BeforeMethod
    public void initBook() {
        var book = booksConfig.getRandomBook();
        var author = book
                .getAuthors()
                .stream()
                .collect(randomItem());
        authorDto = mapAuthorToAuthorDto(book.getId(), author);
    }

    @ApiVersion(1)
    @Test(description = "Validates successful retrieval of a specific author by id")
    public void verifyGetAuthorByIdPositiveTest() {
        var books = authorClient
                .getAuthorById(authorDto.getId())
                .expectStatusCode(SC_OK)
                .getBodyAs(AuthorDto.class);

        assertThat(books)
                .as("Author with id [%s] from API does not match expected".formatted(authorDto.getId()))
                .isEqualTo(authorDto);
    }
}
