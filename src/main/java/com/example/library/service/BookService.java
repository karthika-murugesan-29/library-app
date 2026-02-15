
package com.example.library.service;

import com.example.library.dto.BookDto;
import com.example.library.model.Book;
import com.example.library.repository.BookRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {

    private final BookRepository repo;

    public BookService(BookRepository repo) {
        this.repo = repo;
    }

    public Book addBook(BookDto dto) {
        Book book = new Book();
        book.setTitle(dto.getTitle());
        book.setAuthor(dto.getAuthor());
        book.setAvailable(true);
        return repo.save(book);
    }

    public Book borrowBook(Long id) {
        Book book = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Not found"));

        if (!book.isAvailable()) {
            throw new RuntimeException("Already borrowed");
        }

        book.setAvailable(false);
        return repo.save(book);
    }

    public List<Book> getAll() {
        return repo.findAll();
    }
}
