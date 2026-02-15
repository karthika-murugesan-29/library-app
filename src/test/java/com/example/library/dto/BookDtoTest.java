package com.example.library.dto;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BookDtoTest {

    @Test
    void gettersAndSetters() {
        BookDto dto = new BookDto();
        dto.setTitle("Clean Code");
        dto.setAuthor("Robert C. Martin");

        assertEquals("Clean Code", dto.getTitle());
        assertEquals("Robert C. Martin", dto.getAuthor());
    }
}
