package com.flashsuppressor.java.lab.service.impl;

import com.flashsuppressor.java.lab.repository.BookRepository;
import com.flashsuppressor.java.lab.service.BookService;
import com.flashsuppressor.java.lab.service.TestServiceConfiguration;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

@ExtendWith(MockitoExtension.class)
@ContextConfiguration(classes = TestServiceConfiguration.class)
public class BookServiceImplTest {

    @Autowired
    private BookRepository repository;
    @Autowired
    private BookService service;

    //TODO add tests
}
