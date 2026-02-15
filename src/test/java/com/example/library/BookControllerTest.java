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
    private BookService service;
    private ObjectMapper mapper = new ObjectMapper();

    @BeforeEach
    void setup() {
        service = Mockito.mock(BookService.class);
        BookController controller = new BookController(service);
        mvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    void addBookEndpoint() throws Exception {
        BookDto dto = new BookDto();
        dto.setTitle("T");
        dto.setAuthor("A");

        Book saved = new Book(1L, "T", "A", true);
        Mockito.when(service.addBook(Mockito.any())).thenReturn(saved);

        mvc.perform(post("/books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("T"));
    }

    @Test
    void borrowEndpointSuccess() throws Exception {
        Book b = new Book(2L, "X", "Y", false);
        Mockito.when(service.borrowBook(2L)).thenReturn(b);

        mvc.perform(put("/books/borrow/2"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.available").value(false));
    }

    @Test
    void listEndpoint() throws Exception {
        Mockito.when(service.getAll()).thenReturn(Arrays.asList(new Book(1L, "A", "B", true)));

        mvc.perform(get("/books"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].title").value("A"));
    }
}
