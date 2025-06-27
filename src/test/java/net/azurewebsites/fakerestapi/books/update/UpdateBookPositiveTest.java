package net.azurewebsites.fakerestapi.books.update;

import net.azurewebsites.fakerestapi.books.AbstractBooksRunner;
import net.azurewebsites.fakerestapi.models.author.Book;
import net.azurewebsites.fakerestapi.models.dtos.BookDto;
import net.azurewebsites.fakerestapi.utils.annotations.ApiVersion;
import net.azurewebsites.fakerestapi.utils.annotations.epics.BookEpic;
import org.assertj.core.api.SoftAssertions;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static net.azurewebsites.fakerestapi.models.repos.BookDtoRepo.getRandomBookDto;
import static org.apache.http.HttpStatus.SC_OK;

@BookEpic
public class UpdateBookPositiveTest extends AbstractBooksRunner {

    private Book book;
    private BookDto existingBookDto;
    private BookDto bookUpdateDto;

    @BeforeMethod
    public void initBookToUpdate() {
        book = booksPool.borrowBook();
        existingBookDto = mapBookToBookDto(book);
        bookUpdateDto = getRandomBookDto();
    }


    @ApiVersion(1)
    @Test(description = "Validates successful update of an existing book")
    public void verifyUpdateBookPositiveTest() {
        var updatedBookResponseDto = bookClient
                .updateBookById(existingBookDto.getId(), bookUpdateDto)
                .expectStatusCode(SC_OK)
                .getBodyAs(BookDto.class);

        var soft = new SoftAssertions();
        soft.assertThat(updatedBookResponseDto)
                .as("Created book does not match the request payload")
                .isEqualTo(bookUpdateDto);

        var books = bookClient
                .getBookById(bookUpdateDto.getId())
                .expectStatusCode(SC_OK)
                .getBodyAs(BookDto.class);

        soft.assertThat(books)
                .as("Book with id [%s] from API does not match expected".formatted(bookUpdateDto.getId()))
                .isEqualTo(bookUpdateDto);
        soft.assertAll();
    }

    @AfterMethod
    public void returnBookToThePool() {
        bookClient
                .updateBookById(bookUpdateDto.getId(), existingBookDto)
                .expectStatusCode(SC_OK);

        booksPool.returnBook(book);
    }
}
