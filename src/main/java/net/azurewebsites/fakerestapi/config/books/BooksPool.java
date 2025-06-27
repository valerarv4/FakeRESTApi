package net.azurewebsites.fakerestapi.config.books;

import net.azurewebsites.fakerestapi.models.author.Book;

import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import static java.lang.Thread.currentThread;
import static java.util.Objects.requireNonNull;

public class BooksPool {

    private final BlockingQueue<Book> available;

    public BooksPool(BooksConfig config) {
        this.available = new LinkedBlockingQueue<>(config.getBooks());
    }

    public Book borrowBook() {
        try {
            return available.take();
        } catch (InterruptedException e) {
            currentThread().interrupt();
            throw new RuntimeException("Interrupted while acquiring Book", e);
        }
    }

    public void returnBook(Book book) {
        requireNonNull(book);
        available.offer(book);
    }

    public List<Book> borrowAllAvailableBooks() {
        var availableBooks = available
                .stream()
                .toList();

        available.removeAll(availableBooks);

        return availableBooks;
    }

    public void returnBooks(List<Book> books) {
        requireNonNull(books);
        books.forEach(this::returnBook);
    }
}
