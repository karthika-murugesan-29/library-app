package com.example.library;

import com.example.library.model.Book;
import com.example.library.repository.BookRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class BookRepositoryTest {

    @Autowired
    private BookRepository repo;

    @Test
    void saveAndFind() {
        Book b = new Book();
        b.setTitle("RepoTitle");
        b.setAuthor("RepoAuthor");
        b.setAvailable(true);

        Book saved = repo.save(b);
        Optional<Book> found = repo.findById(saved.getId());

        assertTrue(found.isPresent());
        assertEquals("RepoTitle", found.get().getTitle());
    }
}
