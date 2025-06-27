package net.azurewebsites.fakerestapi.authors.create;

import net.azurewebsites.fakerestapi.authors.AbstractAuthorsRunner;
import net.azurewebsites.fakerestapi.models.author.Book;
import net.azurewebsites.fakerestapi.models.dtos.AuthorDto;
import net.azurewebsites.fakerestapi.utils.annotations.ApiVersion;
import net.azurewebsites.fakerestapi.utils.annotations.epics.AuthorEpic;
import org.assertj.core.api.SoftAssertions;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static net.azurewebsites.fakerestapi.models.repos.AuthorDtoRepo.getRandomAuthorDto;
import static org.apache.http.HttpStatus.SC_OK;

@AuthorEpic
public class CreateAuthorPositiveTest extends AbstractAuthorsRunner {

    private Book book;
    private AuthorDto authorToCreateDto;

    @BeforeMethod
    public void initAuthor() {
        book = booksPool.borrowBook();
        authorToCreateDto = getRandomAuthorDto(book.getId());
    }

    @ApiVersion(1)
    @Test(description = "Validates successful creation of a new author")
    public void verifyCreateAuthorPositiveTest() {
        var createdAuthorResponseDto = authorClient
                .createAuthor(authorToCreateDto)
                .expectStatusCode(SC_OK)
                .getBodyAs(AuthorDto.class);

        var soft = new SoftAssertions();
        soft.assertThat(createdAuthorResponseDto)
                .as("Created author does not match the request payload")
                .isEqualTo(authorToCreateDto);

        var authorDto = authorClient
                .getAuthorById(authorToCreateDto.getId())
                .expectStatusCode(SC_OK)
                .getBodyAs(AuthorDto.class);

        soft.assertThat(authorDto)
                .as("Author with id [%s] from API does not match expected".formatted(authorToCreateDto.getId()))
                .isEqualTo(authorToCreateDto);
        soft.assertAll();
    }

    @AfterMethod
    public void returnBookToThePool() {
        booksPool.returnBook(book);
    }
}
