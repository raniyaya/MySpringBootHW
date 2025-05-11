package com.rookies3.myspringbootlab.service;

import com.rookies3.myspringbootlab.controller.dto.BookCreateRequest;
import com.rookies3.myspringbootlab.controller.dto.BookUpdateRequest;
import com.rookies3.myspringbootlab.controller.dto.BookResponse;
import com.rookies3.myspringbootlab.entity.Book;
import com.rookies3.myspringbootlab.repository.BookRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class BookService {

    private final BookRepository bookRepository;

    public List<BookResponse> getAllBooks() {
        return BookResponse.from(bookRepository.findAll());
    }

    public BookResponse getBookById(Long id) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("도서를 찾을 수 없습니다."));
        return new BookResponse(book);
    }

    public BookResponse getBookByIsbn(String isbn) {
        Book book = bookRepository.findByIsbn(isbn)
                .orElseThrow(() -> new EntityNotFoundException("ISBN으로 도서를 찾을 수 없습니다."));
        return new BookResponse(book);
    }

    public List<BookResponse> getBooksByAuthor(String author) {
        return BookResponse.from(bookRepository.findByAuthor(author));
    }

    @Transactional
    public BookResponse createBook(BookCreateRequest request) {
        Book saved = bookRepository.save(request.toEntity());
        return new BookResponse(saved);
    }

    @Transactional
    public BookResponse updateBook(Long id, BookUpdateRequest request) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("도서를 찾을 수 없습니다."));

        book.setTitle(request.getTitle());
        book.setAuthor(request.getAuthor());
        book.setPrice(request.getPrice());
        book.setPublishDate(request.getPublishDate());

        return new BookResponse(book);
    }

    @Transactional
    public void deleteBook(Long id) {
        bookRepository.deleteById(id);
    }
}
