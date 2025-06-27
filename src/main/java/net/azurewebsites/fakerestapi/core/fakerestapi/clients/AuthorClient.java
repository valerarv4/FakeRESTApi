package net.azurewebsites.fakerestapi.core.fakerestapi.clients;

import lombok.NoArgsConstructor;
import net.azurewebsites.fakerestapi.core.restassuredclient.HttpResponseWrapper;
import net.azurewebsites.fakerestapi.models.dtos.AuthorDto;

import static lombok.AccessLevel.PRIVATE;
import static net.azurewebsites.fakerestapi.core.fakerestapi.clients.AuthorClient.BookResource.*;

public class AuthorClient extends AbstractClient {

    public HttpResponseWrapper getAuthors() {
        return getClient().get(AUTHOR_BASE_URL);
    }

    public HttpResponseWrapper createAuthor(AuthorDto body) {
        return getClient()
                .body(body)
                .post(AUTHOR_BASE_URL);
    }

    public HttpResponseWrapper getAuthorsByBookId(int idBook) {
        return getClient().get(AUTHOR_GET_BY_BOOK_ID_URL, idBook);
    }

    public HttpResponseWrapper getAuthorById(int id) {
        return getClient().get(AUTHOR_BASE_URL_WITH_ID, id);
    }

    public HttpResponseWrapper updateAuthorById(int id, AuthorDto body) {
        return getClient()
                .body(body)
                .put(AUTHOR_BASE_URL_WITH_ID, id);
    }

    public HttpResponseWrapper deleteAuthorById(int id) {
        return getClient().delete(AUTHOR_BASE_URL_WITH_ID, id);
    }

    @NoArgsConstructor(access = PRIVATE)
    static class BookResource {
        static final String AUTHOR_BASE_URL = "/Authors";
        static final String AUTHOR_BASE_URL_WITH_ID = AUTHOR_BASE_URL + "/{id}";
        static final String AUTHOR_GET_BY_BOOK_ID_URL = AUTHOR_BASE_URL + "/authors/books/{idBook}";
    }
}
