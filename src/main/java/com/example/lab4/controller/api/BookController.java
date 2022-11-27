package com.example.lab4.controller.api;


import com.example.lab4.dto.BookDto;
import com.example.lab4.model.Book;
import com.example.lab4.service.BookService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/books", produces = {"application/json", "application/xml"})
public class BookController {
    final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping("/{id}")
    public Book findById(@PathVariable Integer id){
        return bookService.findById(id);
    }

    @GetMapping("/all")
    public List<Book> findAll(){
        return bookService.findAll();
    }

    @PostMapping("/update")
    public void update(@RequestBody Book book){
        bookService.update(book.getId(), book.getTitle(), book.getAuthorName(), book.getGenre(), book.getPrice());
    }

    @PostMapping("/create")
    public Book create(@RequestBody BookDto bookDto){
        return bookService.create(bookDto.getTitle(), bookDto.getAuthorName(),
                bookDto.getGenre(), bookDto.getPrice());
    }

    @DeleteMapping("/delete/{id}")
    public Book delete(@PathVariable Integer id){
        return bookService.delete(id);
    }

}
