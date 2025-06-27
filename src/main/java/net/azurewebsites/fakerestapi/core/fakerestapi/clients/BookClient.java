package net.azurewebsites.fakerestapi.core.fakerestapi.clients;

import io.qameta.allure.Step;
import lombok.NoArgsConstructor;
import net.azurewebsites.fakerestapi.core.restassuredclient.HttpResponseWrapper;
import net.azurewebsites.fakerestapi.models.dtos.BookDto;

import static lombok.AccessLevel.PRIVATE;
import static net.azurewebsites.fakerestapi.core.fakerestapi.clients.BookClient.BookResource.BOOK_BASE_URL;
import static net.azurewebsites.fakerestapi.core.fakerestapi.clients.BookClient.BookResource.BOOK_BASE_URL_WITH_ID;

public class BookClient extends AbstractClient {

    @Step("Got books")
    public HttpResponseWrapper getBooks() {
        return getClient().get(BOOK_BASE_URL);
    }

    @Step("Created book with body: {body}")
    public HttpResponseWrapper createBook(BookDto body) {
        return getClient()
                .body(body)
                .post(BOOK_BASE_URL);
    }

    @Step("Got book by id: {id}")
    public HttpResponseWrapper getBookById(int id) {
        return getClient().get(BOOK_BASE_URL_WITH_ID, id);
    }

    @Step("Updated book by id: {id} with body: {body}")
    public HttpResponseWrapper updateBookById(int id, BookDto body) {
        return getClient()
                .body(body)
                .put(BOOK_BASE_URL_WITH_ID, id);
    }

    @Step("Deleted book by id: {id}")
    public HttpResponseWrapper deleteBookById(int id) {
        return getClient().delete(BOOK_BASE_URL_WITH_ID, id);
    }

    @NoArgsConstructor(access = PRIVATE)
    static class BookResource {
        static final String BOOK_BASE_URL = "/Books";
        static final String BOOK_BASE_URL_WITH_ID = BOOK_BASE_URL + "/{id}";
    }
}
