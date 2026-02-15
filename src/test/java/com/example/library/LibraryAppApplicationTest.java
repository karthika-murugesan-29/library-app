package com.example.library;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

class LibraryAppApplicationTest {

    @Test
    void mainStartsWithoutException() {
        assertDoesNotThrow(() -> LibraryAppApplication.main(new String[]{}));
    }
}
