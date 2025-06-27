package net.azurewebsites.fakerestapi;

import net.azurewebsites.fakerestapi.config.books.BooksConfig;
import net.azurewebsites.fakerestapi.config.books.BooksPool;
import net.azurewebsites.fakerestapi.utils.listeners.ApiVersionFilterListener;
import net.azurewebsites.fakerestapi.utils.listeners.ThreadCountListener;
import org.modelmapper.ModelMapper;
import org.testng.annotations.Listeners;

@Listeners({ApiVersionFilterListener.class, ThreadCountListener.class})
public abstract class AbstractTestRunner {

    protected static BooksConfig booksConfig;
    protected static BooksPool booksPool;
    protected static ModelMapper modelMapper;

    static {
        booksConfig = new BooksConfig();
        booksPool = new BooksPool(booksConfig);
        modelMapper = new ModelMapper();
    }
}
