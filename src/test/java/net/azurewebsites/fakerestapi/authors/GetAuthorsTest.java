package net.azurewebsites.fakerestapi.authors;

import net.azurewebsites.fakerestapi.models.author.Book;
import net.azurewebsites.fakerestapi.models.dtos.AuthorDto;
import net.azurewebsites.fakerestapi.utils.annotations.ApiVersion;
import net.azurewebsites.fakerestapi.utils.annotations.epics.AuthorEpic;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.List;

import static org.apache.http.HttpStatus.SC_OK;
import static org.assertj.core.api.Assertions.assertThat;

@AuthorEpic
public class GetAuthorsTest extends AbstractAuthorsRunner {

    private List<Book> allAvailableBooks;
    private List<AuthorDto> allAvailableAuthorsAsDto;

    @BeforeMethod
    public void initAuthors() {
        allAvailableBooks = booksPool.borrowAllAvailableBooks();
        allAvailableAuthorsAsDto = allAvailableBooks
                .stream()
                .flatMap(book -> book
                        .getAuthors()
                        .stream()
                        .map(author -> mapAuthorToAuthorDto(book.getId(), author)))
                .toList();
    }

    @ApiVersion(1)
    @Test(description = "Validates successful retrieval of all authors")
    public void verifyGetAuthorsTest() {
        var books = authorClient
                .getAuthors()
                .expectStatusCode(SC_OK)
                .getBodyAsList(AuthorDto.class);

        assertThat(books)
                .as("Response does not contain all expected authors")
                .containsAll(allAvailableAuthorsAsDto);
    }

    @AfterMethod
    public void returnBooksToThePool() {
        booksPool.returnBooks(allAvailableBooks);
    }
}
