package net.azurewebsites.fakerestapi.books;

import net.azurewebsites.fakerestapi.AbstractTestRunner;
import net.azurewebsites.fakerestapi.core.fakerestapi.clients.BookClient;
import net.azurewebsites.fakerestapi.models.dtos.BookDto;
import net.azurewebsites.fakerestapi.models.author.Book;

public abstract class AbstractBooksRunner extends AbstractTestRunner {

    protected final BookClient bookClient = new BookClient();

    protected BookDto mapBookToBookDto(Book book) {
        return modelMapper.map(book, BookDto.class);
    }
}
