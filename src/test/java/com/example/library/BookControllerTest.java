package com.example.library;

import com.example.library.controller.BookController;
import com.example.library.dto.BookDto;
import com.example.library.model.Book;
import com.example.library.service.BookService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class BookControllerTest {

    private MockMvc mvc;
    private ObjectMapper mapper = new ObjectMapper();

    @Test
    void addBookEndpoint() throws Exception {
        BookDto dto = new BookDto();
        dto.setTitle("T");
        dto.setAuthor("A");

        Book saved = new Book(1L, "T", "A", true);
        BookService service = new BookService(null) {
            @Override
            public Book addBook(BookDto d) {
                return saved;
            }

            @Override
            public Book borrowBook(Long id) {
                return null;
            }

            @Override
            public java.util.List<Book> getAll() {
                return null;
            }
        };

        mvc = MockMvcBuilders.standaloneSetup(new BookController(service)).build();

        mvc.perform(post("/books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("T"));
    }

    @Test
    void borrowEndpointSuccess() throws Exception {
        Book b = new Book(2L, "X", "Y", false);
        BookService service = new BookService(null) {
            @Override
            public Book addBook(BookDto d) { return null; }

            @Override
            public Book borrowBook(Long id) { return b; }

            @Override
            public java.util.List<Book> getAll() { return null; }
        };

        mvc = MockMvcBuilders.standaloneSetup(new BookController(service)).build();

        mvc.perform(put("/books/borrow/2"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.available").value(false));
    }

    @Test
    void listEndpoint() throws Exception {
        java.util.List<Book> list = Arrays.asList(new Book(1L, "A", "B", true));
        BookService service = new BookService(null) {
            @Override
            public Book addBook(BookDto d) { return null; }

            @Override
            public Book borrowBook(Long id) { return null; }

            @Override
            public java.util.List<Book> getAll() { return list; }
        };

        mvc = MockMvcBuilders.standaloneSetup(new BookController(service)).build();

        mvc.perform(get("/books"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].title").value("A"));
    }
}
