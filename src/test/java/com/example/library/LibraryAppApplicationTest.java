package com.example.library;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

class LibraryAppApplicationTest {

    @Test
    void mainStartsWithoutException() {
        String[] args = new String[]{"--spring.main.web-application-type=none"};
        assertDoesNotThrow(() -> LibraryAppApplication.main(args));
    }
}
