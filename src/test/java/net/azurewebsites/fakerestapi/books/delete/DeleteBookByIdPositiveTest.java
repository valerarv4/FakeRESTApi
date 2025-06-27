package net.azurewebsites.fakerestapi.books.delete;

import net.azurewebsites.fakerestapi.books.AbstractBooksRunner;
import net.azurewebsites.fakerestapi.models.dtos.BookDto;
import net.azurewebsites.fakerestapi.utils.annotations.ApiVersion;
import net.azurewebsites.fakerestapi.utils.annotations.epics.BookEpic;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static net.azurewebsites.fakerestapi.models.repos.BookDtoRepo.getRandomBookDto;
import static org.apache.http.HttpStatus.SC_NOT_FOUND;
import static org.apache.http.HttpStatus.SC_OK;

@BookEpic
public class DeleteBookByIdPositiveTest extends AbstractBooksRunner {

    private BookDto bookToCreateDto;

    @BeforeMethod
    public void initBook() {
        bookToCreateDto = getRandomBookDto();

        bookClient
                .createBook(bookToCreateDto)
                .expectStatusCode(SC_OK);

        bookClient
                .getBookById(bookToCreateDto.getId())
                .expectStatusCode(SC_OK);
    }

    @ApiVersion(1)
    @Test(description = "Validates successful deletion of a book by id")
    public void verifyDeleteBookByIdPositiveTest() {
        bookClient
                .deleteBookById(bookToCreateDto.getId())
                .expectStatusCode(SC_OK);

        bookClient
                .getBookById(bookToCreateDto.getId())
                .expectStatusCode(SC_NOT_FOUND);
    }
}
