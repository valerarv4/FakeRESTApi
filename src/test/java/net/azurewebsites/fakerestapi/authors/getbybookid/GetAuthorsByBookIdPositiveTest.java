package net.azurewebsites.fakerestapi.authors.getbybookid;

import net.azurewebsites.fakerestapi.authors.AbstractAuthorsRunner;
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
public class GetAuthorsByBookIdPositiveTest extends AbstractAuthorsRunner {

    private Book book;
    private List<AuthorDto> authorDtos;

    @BeforeMethod
    public void initBook() {
        book = booksPool.borrowBook();
        authorDtos = book
                .getAuthors()
                .stream()
                .map(author -> mapAuthorToAuthorDto(book.getId(), author))
                .toList();
    }

    @ApiVersion(1)
    @Test(description = "Validates successful retrieval of authors for a given book id")
    public void verifyGetAuthorsByBookIdPositiveTest() {
        var actualAuthors = authorClient
                .getAuthorsByBookId(book.getId())
                .expectStatusCode(SC_OK)
                .getBodyAsList(AuthorDto.class);

        assertThat(actualAuthors)
                .as("Authors returned for book id [%s] do not match expected".formatted(book.getId()))
                .isEqualTo(authorDtos);
    }

    @AfterMethod
    public void returnBookToThePool() {
        booksPool.returnBook(book);
    }
}
