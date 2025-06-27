package net.azurewebsites.fakerestapi.models.repos;

import net.azurewebsites.fakerestapi.models.dtos.BookDto;

import static net.azurewebsites.fakerestapi.utils.DateTimeUtils.*;
import static net.azurewebsites.fakerestapi.utils.RandomUtils.*;

public class BookDtoRepo {

    public static BookDto getRandomBookDto() {
        var publishDate = offsetDateTimeToString(getRandomPastDate(), ISO_WITH_MILLIS);

        return BookDto
                .builder()
                .id(getRandomPositiveInt())
                .title(FAKER.book().title())
                .description(getRandomAlphabeticString(250))
                .pageCount(getRandomPositiveInt())
                .excerpt(getRandomAlphabeticString(250))
                .publishDate(publishDate)
                .build();
    }
}
