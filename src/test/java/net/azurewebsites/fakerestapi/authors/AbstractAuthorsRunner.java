package net.azurewebsites.fakerestapi.authors;

import net.azurewebsites.fakerestapi.AbstractTestRunner;
import net.azurewebsites.fakerestapi.core.fakerestapi.clients.AuthorClient;
import net.azurewebsites.fakerestapi.models.dtos.AuthorDto;
import net.azurewebsites.fakerestapi.models.author.Author;

public abstract class AbstractAuthorsRunner extends AbstractTestRunner {

    protected final AuthorClient authorClient = new AuthorClient();

    protected AuthorDto mapAuthorToAuthorDto(int bookId, Author author) {
        var authorDto = modelMapper.map(author, AuthorDto.class);
        authorDto.setIdBook(bookId);

        return authorDto;
    }
}
