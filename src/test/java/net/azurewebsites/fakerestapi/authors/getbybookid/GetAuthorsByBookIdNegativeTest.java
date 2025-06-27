package net.azurewebsites.fakerestapi.authors.getbybookid;

import net.azurewebsites.fakerestapi.authors.AbstractAuthorsRunner;
import net.azurewebsites.fakerestapi.models.dtos.ErrorResponseDto;
import net.azurewebsites.fakerestapi.utils.annotations.ApiVersion;
import net.azurewebsites.fakerestapi.utils.annotations.epics.AuthorEpic;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static net.azurewebsites.fakerestapi.models.repos.ErrorResponseDtoRepo.getErrorResponseDto;
import static org.apache.http.HttpStatus.SC_NOT_FOUND;
import static org.assertj.core.api.Assertions.assertThat;

@AuthorEpic
public class GetAuthorsByBookIdNegativeTest extends AbstractAuthorsRunner {

    private ErrorResponseDto expectedResponseError;

    @BeforeMethod
    public void initResponseError() {
        expectedResponseError = getErrorResponseDto("Not Found", SC_NOT_FOUND);
    }

    @ApiVersion(1)
    @Test(description = "Verifies error response when retrieving authors by an invalid book id")
    public void verifyGetAuthorsByBookIdNegativeTest() {
        var actualResponseError = authorClient
                .getAuthorsByBookId(0)
                .expectStatusCode(expectedResponseError.getStatus())
                .getBodyAs(ErrorResponseDto.class);

        assertThat(actualResponseError)
                .as("Error response for invalid book id does not match expected")
                .isEqualTo(expectedResponseError);
    }
}
