package net.azurewebsites.fakerestapi.models.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthorDto {

    private Integer id;
    private Integer idBook;
    private String firstName;
    private String lastName;
}
