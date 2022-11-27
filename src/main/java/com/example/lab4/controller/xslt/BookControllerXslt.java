package com.example.lab4.controller.xslt;


import com.example.lab4.model.Book;
import com.example.lab4.repository.BookRepository;
import com.example.lab4.service.BookService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import java.io.ByteArrayInputStream;
import java.util.List;

@Controller
@RequestMapping("/xslt/books")
public class BookControllerXslt {

    final BookService bookService;

    public BookControllerXslt(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping
    public ModelAndView get() throws JsonProcessingException {
        List<Book> books = bookService.findAll();

        XmlMapper xmlMapper = new XmlMapper();
        String xml = xmlMapper.writeValueAsString(books);

        Source source = new StreamSource(new ByteArrayInputStream(xml.getBytes()));
        ModelAndView modelAndView = new ModelAndView("books");
        modelAndView.addObject("xmlSource", source);

        return modelAndView;
    }
}
