package net.azurewebsites.fakerestapi.models.repos;

import net.azurewebsites.fakerestapi.models.dtos.ErrorResponseDto;

public class ErrorResponseDtoRepo {

    public static ErrorResponseDto getErrorResponseDto(String title, int status) {
        return new ErrorResponseDto(title, status);
    }
}
