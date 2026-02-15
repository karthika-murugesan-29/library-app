
package com.example.library.controller;

import com.example.library.dto.BookDto;
import com.example.library.model.Book;
import com.example.library.service.BookService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/books")
public class BookController {

    private final BookService service;

    public BookController(BookService service) {
        this.service = service;
    }

    @PostMapping
    public Book add(@RequestBody BookDto dto) {
        return service.addBook(dto);
    }

    @PutMapping("/borrow/{id}")
    public Book borrow(@PathVariable Long id) {
        return service.borrowBook(id);
    }

    @GetMapping
    public List<Book> list() {
        return service.getAll();
    }
}
