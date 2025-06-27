package net.azurewebsites.fakerestapi.models.repos;

import net.azurewebsites.fakerestapi.models.dtos.AuthorDto;

import static net.azurewebsites.fakerestapi.utils.RandomUtils.FAKER;
import static net.azurewebsites.fakerestapi.utils.RandomUtils.getRandomPositiveInt;

public class AuthorDtoRepo {

    public static AuthorDto getRandomAuthorDto(int bookId) {
        var randomName = FAKER.name();

        return AuthorDto
                .builder()
                .id(getRandomPositiveInt())
                .idBook(bookId)
                .firstName(randomName.firstName())
                .lastName(randomName.lastName())
                .build();
    }
}
