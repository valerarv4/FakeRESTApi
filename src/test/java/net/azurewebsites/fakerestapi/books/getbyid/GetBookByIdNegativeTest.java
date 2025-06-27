package net.azurewebsites.fakerestapi.books.getbyid;

import net.azurewebsites.fakerestapi.books.AbstractBooksRunner;
import net.azurewebsites.fakerestapi.models.dtos.ErrorResponseDto;
import net.azurewebsites.fakerestapi.utils.annotations.ApiVersion;
import net.azurewebsites.fakerestapi.utils.annotations.epics.BookEpic;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static net.azurewebsites.fakerestapi.models.repos.ErrorResponseDtoRepo.getErrorResponseDto;
import static org.apache.http.HttpStatus.SC_NOT_FOUND;
import static org.assertj.core.api.Assertions.assertThat;

@BookEpic
public class GetBookByIdNegativeTest extends AbstractBooksRunner {

    private ErrorResponseDto expectedResponseError;

    @BeforeMethod
    public void initResponseError() {
        expectedResponseError = getErrorResponseDto("Not Found", SC_NOT_FOUND);
    }

    @ApiVersion(1)
    @Test(description = "Validates error response when retrieving a book by a non-existent id")
    public void verifyGetBookByIdNegativeTest() {
        var actualResponseError = bookClient
                .getBookById(0)
                .expectStatusCode(expectedResponseError.getStatus())
                .getBodyAs(ErrorResponseDto.class);

        assertThat(actualResponseError)
                .as("Error response for invalid book Id does not match expected")
                .isEqualTo(expectedResponseError);
    }
}
