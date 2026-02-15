package com.example.library;

import com.example.library.controller.BookController;
import com.example.library.dto.BookDto;
import com.example.library.model.Book;
import com.example.library.service.BookService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class BookControllerErrorTest {

    private MockMvc mvc;
    private ObjectMapper mapper = new ObjectMapper();

    @Test
    void borrowNotFoundReturnsServerError() throws Exception {
        BookService service = new BookService(null) {
            @Override
            public Book addBook(BookDto d) { return null; }

            @Override
            public Book borrowBook(Long id) { throw new RuntimeException("Not found"); }

            @Override
            public java.util.List<Book> getAll() { return null; }
        };

        mvc = MockMvcBuilders.standaloneSetup(new BookController(service))
            .setControllerAdvice(new TestExceptionHandler())
            .build();

        mvc.perform(put("/books/borrow/999"))
                .andExpect(status().isInternalServerError());
    }

    @Test
    void addBookServiceErrorReturnsServerError() throws Exception {
        BookDto dto = new BookDto();
        dto.setTitle("T");
        dto.setAuthor("A");

        BookService service = new BookService(null) {
            @Override
            public Book addBook(BookDto d) { throw new RuntimeException("db down"); }

            @Override
            public Book borrowBook(Long id) { return null; }

            @Override
            public java.util.List<Book> getAll() { return null; }
        };

        mvc = MockMvcBuilders.standaloneSetup(new BookController(service))
                .setControllerAdvice(new TestExceptionHandler())
                .build();
        mvc.perform(post("/books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(dto)))
                .andExpect(status().isInternalServerError());

    }

    @org.springframework.web.bind.annotation.ControllerAdvice
    static class TestExceptionHandler {
        @org.springframework.web.bind.annotation.ExceptionHandler(Exception.class)
        public org.springframework.http.ResponseEntity<String> handle(Exception ex) {
            return org.springframework.http.ResponseEntity.status(500).body(ex.getMessage());
        }
    }
}
