package com.example.library;

import com.example.library.dto.BookDto;
import com.example.library.model.Book;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class BookControllerIntegrationTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper mapper;

    @Test
    void addListAndBorrowFlow() throws Exception {
        BookDto dto = new BookDto();
        dto.setTitle("IntTitle");
        dto.setAuthor("IntAuthor");

        String payload = mapper.writeValueAsString(dto);

        String response = mvc.perform(post("/books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(payload))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("IntTitle"))
                .andReturn().getResponse().getContentAsString();

        Book created = mapper.readValue(response, Book.class);

        mvc.perform(put("/books/borrow/" + created.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.available").value(false));

        mvc.perform(get("/books"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray());
    }

    @Test
        void borrowNonExistingReturnsServerError() throws Exception {
                org.junit.jupiter.api.Assertions.assertThrows(Exception.class, () ->
                                mvc.perform(put("/books/borrow/999999"))
                );
        }
}
