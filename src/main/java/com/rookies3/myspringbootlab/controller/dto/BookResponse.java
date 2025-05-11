package com.rookies3.myspringbootlab.controller.dto;

import com.rookies3.myspringbootlab.entity.Book;
import lombok.Getter;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public class BookResponse {

    private Long id;
    private String title;
    private String author;
    private String isbn;
    private Integer price;
    private LocalDate publishDate;

    public BookResponse(Book book) {
        this.id = book.getId();
        this.title = book.getTitle();
        this.author = book.getAuthor();
        this.isbn = book.getIsbn();
        this.price = book.getPrice();
        this.publishDate = book.getPublishDate();
    }

    public static List<BookResponse> from(List<Book> books) {
        return books.stream().map(BookResponse::new).collect(Collectors.toList());
    }
}
