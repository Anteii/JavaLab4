package com.example.lab4.service;

import com.example.lab4.dto.BookDto;
import com.example.lab4.model.Book;
import com.example.lab4.repository.BookRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {
    final BookRepository bookRepository;
    final AuditService auditService;

    public BookService(BookRepository bookRepository, AuditService auditService) {
        this.bookRepository = bookRepository;
        this.auditService = auditService;
    }

    public Book findById(Integer id){
        return bookRepository.findById(id).orElseThrow();
    }

    public Book create(String title, String authorName, String genre, Double price) {
        Book book = bookRepository.saveAndFlush(new Book(title, authorName, genre, price));

        auditService.log(book, "book", AuditService.EVENT_CREATE);

        return book;
    }

    public Book update(Integer id, String title, String authorName, String genre, Double price) {
        Book oldBook = bookRepository.findById(id).orElseThrow();
        Book newBook = new Book(oldBook.getId(), oldBook.getTitle(), oldBook.getAuthorName(), oldBook.getGenre(), oldBook.getPrice());

        newBook.update(title, authorName, genre, price);

        auditService.log(oldBook, newBook, "book", AuditService.EVENT_UPDATE);

        return bookRepository.saveAndFlush(newBook);
    }

    public Book delete(Integer id) {
        Book book = bookRepository.findById(id).orElseThrow();
        bookRepository.delete(book);

        auditService.log(book, "book", AuditService.EVENT_DELETE);

        return book;
    }

    public List<Book> findAll(){
        return bookRepository.findAll();
    }

}
