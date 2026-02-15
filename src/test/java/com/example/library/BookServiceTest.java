
package com.example.library;

import com.example.library.dto.BookDto;
import com.example.library.model.Book;
import com.example.library.repository.BookRepository;
import com.example.library.service.BookService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class BookServiceTest {

    @Test
    void addBookTest() {
        BookRepository repo = Mockito.mock(BookRepository.class);
        BookService service = new BookService(repo);

        BookDto dto = new BookDto();
        dto.setTitle("Test");
        dto.setAuthor("Author");

        Mockito.when(repo.save(Mockito.any())).thenAnswer(i -> i.getArgument(0));

        Book book = service.addBook(dto);
        assertEquals("Test", book.getTitle());
    }

    @Test
    void borrowBookSuccessTest() {
        BookRepository repo = Mockito.mock(BookRepository.class);
        BookService service = new BookService(repo);

        Book existing = new Book(1L, "Title", "Author", true);
        Mockito.when(repo.findById(1L)).thenReturn(Optional.of(existing));
        Mockito.when(repo.save(Mockito.any())).thenAnswer(i -> i.getArgument(0));

        Book borrowed = service.borrowBook(1L);
        assertFalse(borrowed.isAvailable());
        Mockito.verify(repo).save(Mockito.any());
    }

    @Test
    void borrowBookAlreadyBorrowedThrows() {
        BookRepository repo = Mockito.mock(BookRepository.class);
        BookService service = new BookService(repo);

        Book existing = new Book(2L, "T", "A", false);
        Mockito.when(repo.findById(2L)).thenReturn(Optional.of(existing));

        RuntimeException ex = assertThrows(RuntimeException.class, () -> service.borrowBook(2L));
        assertEquals("Already borrowed", ex.getMessage());
    }

    @Test
    void borrowBookNotFoundThrows() {
        BookRepository repo = Mockito.mock(BookRepository.class);
        BookService service = new BookService(repo);

        Mockito.when(repo.findById(99L)).thenReturn(Optional.empty());

        RuntimeException ex = assertThrows(RuntimeException.class, () -> service.borrowBook(99L));
        assertEquals("Not found", ex.getMessage());
    }

    @Test
    void getAllReturnsList() {
        BookRepository repo = Mockito.mock(BookRepository.class);
        BookService service = new BookService(repo);

        List<Book> list = Arrays.asList(new Book(1L, "A", "X", true), new Book(2L, "B", "Y", true));
        Mockito.when(repo.findAll()).thenReturn(list);

        List<Book> result = service.getAll();
        assertEquals(2, result.size());
        assertEquals("A", result.get(0).getTitle());
    }
}
