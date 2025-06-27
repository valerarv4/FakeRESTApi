package net.azurewebsites.fakerestapi.models.author;

import lombok.Data;

import java.time.OffsetDateTime;
import java.util.List;

@Data
public class Book {

    private int id;
    private String title;
    private List<Author> authors;
    private String description;
    private int pageCount;
    private String excerpt;
    private OffsetDateTime publishDate;
}
