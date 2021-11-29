package com.flashsuppressor.java.lab.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = BookController.class)
class BookControllerTest {

    @Test
    void find() {
    }

    @Test
    void findAll() {
    }

    @Test
    void create() {
    }

    @Test
    void createAll() {
    }

    @Test
    void update() {
    }

    @Test
    void delete() {
    }
}