package com.flashsuppressor.java.lab.service.impl;

import com.flashsuppressor.java.lab.repository.GenreRepository;
import com.flashsuppressor.java.lab.service.GenreService;
import com.flashsuppressor.java.lab.service.TestServiceConfiguration;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = TestServiceConfiguration.class)
public class GenreServiceImplTest {
    @Autowired
    private GenreRepository repository;
    @Autowired
    private GenreService service;

    //TODO add tests
}
