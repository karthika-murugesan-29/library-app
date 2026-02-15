package com.example.library.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BookTest {

    @Test
    void constructorAndSetters() {
        Book book = new Book(1L, "The Hobbit", "J.R.R. Tolkien", true);

        assertEquals(1L, book.getId());
        assertEquals("The Hobbit", book.getTitle());
        assertEquals("J.R.R. Tolkien", book.getAuthor());
        assertTrue(book.isAvailable());

        book.setAvailable(false);
        assertFalse(book.isAvailable());

        book.setId(42L);
        assertEquals(42L, book.getId());
    }
}
