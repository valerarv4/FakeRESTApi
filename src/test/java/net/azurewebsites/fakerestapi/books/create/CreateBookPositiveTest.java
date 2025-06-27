package net.azurewebsites.fakerestapi.books.create;

import net.azurewebsites.fakerestapi.books.AbstractBooksRunner;
import net.azurewebsites.fakerestapi.models.dtos.BookDto;
import net.azurewebsites.fakerestapi.utils.annotations.ApiVersion;
import net.azurewebsites.fakerestapi.utils.annotations.epics.BookEpic;
import org.assertj.core.api.SoftAssertions;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static net.azurewebsites.fakerestapi.models.repos.BookDtoRepo.getRandomBookDto;
import static org.apache.http.HttpStatus.SC_OK;

@BookEpic
public class CreateBookPositiveTest extends AbstractBooksRunner {

    private BookDto bookToCreateDto;

    @BeforeMethod
    public void initBook() {
        bookToCreateDto = getRandomBookDto();
    }

    @ApiVersion(1)
    @Test(description = "Validates successful creation of a new book")
    public void verifyCreateBookPositiveTest() {
        var createdBookResponseDto = bookClient
                .createBook(bookToCreateDto)
                .expectStatusCode(SC_OK)
                .getBodyAs(BookDto.class);

        var soft = new SoftAssertions();
        soft.assertThat(createdBookResponseDto)
                .as("Created book does not match the request payload")
                .isEqualTo(bookToCreateDto);

        var books = bookClient
                .getBookById(bookToCreateDto.getId())
                .expectStatusCode(SC_OK)
                .getBodyAs(BookDto.class);

        soft.assertThat(books)
                .as("Book with id [%s] from API does not match expected".formatted(bookToCreateDto.getId()))
                .isEqualTo(bookToCreateDto);
        soft.assertAll();
    }
}
