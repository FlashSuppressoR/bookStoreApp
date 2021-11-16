package com.flashsuppressor.java.lab.service.impl;

import com.flashsuppressor.java.lab.entity.Author;
import com.flashsuppressor.java.lab.repository.AuthorRepository;
import com.flashsuppressor.java.lab.service.AuthorService;
import com.flashsuppressor.java.lab.service.TestServiceConfiguration;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ContextConfiguration;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockitoExtension.class)
@ContextConfiguration(classes = TestServiceConfiguration.class)
public class AuthorServiceImplTest {

    @Mock
    private AuthorService service;
    @Mock
    private AuthorRepository repository;

    @Test
    void findAllTest() {
        int expectedSize = 2;
        Mockito.when(repository.findAll()).thenReturn(Arrays.asList(new Author(), new Author()));
        int actualSize = service.findAll().size();

        assertEquals(expectedSize, actualSize);
    }

    @Test
    void deleteByIdTest() {
        int validId = 1;
        Mockito.when(repository.deleteById(validId)).thenReturn(true);

        assertTrue(service.deleteById(validId));
    }
}
