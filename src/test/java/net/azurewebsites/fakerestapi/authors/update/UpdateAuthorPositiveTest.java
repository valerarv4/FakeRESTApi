package net.azurewebsites.fakerestapi.authors.update;

import net.azurewebsites.fakerestapi.authors.AbstractAuthorsRunner;
import net.azurewebsites.fakerestapi.models.author.Book;
import net.azurewebsites.fakerestapi.models.dtos.AuthorDto;
import net.azurewebsites.fakerestapi.models.dtos.BookDto;
import net.azurewebsites.fakerestapi.utils.annotations.ApiVersion;
import net.azurewebsites.fakerestapi.utils.annotations.epics.AuthorEpic;
import org.assertj.core.api.SoftAssertions;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static net.azurewebsites.fakerestapi.models.repos.AuthorDtoRepo.getRandomAuthorDto;
import static net.azurewebsites.fakerestapi.utils.StreamUtils.randomItem;
import static org.apache.http.HttpStatus.SC_OK;

@AuthorEpic
public class UpdateAuthorPositiveTest extends AbstractAuthorsRunner {

    private Book book;
    private AuthorDto existingAuthorDto;
    private AuthorDto authorUpdateDto;

    @BeforeMethod
    public void initAuthorToUpdate() {
        book = booksPool.borrowBook();
        var author = book
                .getAuthors()
                .stream()
                .collect(randomItem());
        existingAuthorDto = mapAuthorToAuthorDto(book.getId(), author);
        authorUpdateDto = getRandomAuthorDto(book.getId());
    }

    @ApiVersion(1)
    @Test(description = "Validates successful update of an existing author")
    public void verifyUpdateAuthorPositiveTest() {
        var updatedAuthorResponseDto = authorClient
                .updateAuthorById(existingAuthorDto.getId(), authorUpdateDto)
                .expectStatusCode(SC_OK)
                .getBodyAs(AuthorDto.class);

        var soft = new SoftAssertions();
        soft.assertThat(updatedAuthorResponseDto)
                .as("Created author does not match the request payload")
                .isEqualTo(authorUpdateDto);

        var author = authorClient
                .getAuthorById(authorUpdateDto.getId())
                .expectStatusCode(SC_OK)
                .getBodyAs(BookDto.class);

        soft.assertThat(author)
                .as("Author with id [%s] from API does not match expected".formatted(authorUpdateDto.getId()))
                .isEqualTo(authorUpdateDto);
        soft.assertAll();
    }

    @AfterMethod
    public void returnBookToThePool() {
        authorClient
                .updateAuthorById(authorUpdateDto.getId(), existingAuthorDto)
                .expectStatusCode(SC_OK);

        booksPool.returnBook(book);
    }
}
