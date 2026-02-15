
package com.example.library;

import com.example.library.dto.BookDto;
import com.example.library.model.Book;
import com.example.library.repository.BookRepository;
import com.example.library.service.BookService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

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
}
