package com.flashsuppressor.java.lab.service.impl;

import com.flashsuppressor.java.lab.entity.Author;
import com.flashsuppressor.java.lab.exception.RepositoryException;
import com.flashsuppressor.java.lab.exception.ServiceException;
import com.flashsuppressor.java.lab.repository.AuthorRepository;
import com.flashsuppressor.java.lab.service.AuthorService;
import com.flashsuppressor.java.lab.service.TestServiceConfiguration;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Service;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = TestServiceConfiguration.class)
//@EnableAspectJAutoProxy(proxyTargetClass=true)
public class AuthorServiceImplTest {

    @Autowired
    private AuthorRepository repository;
    @Autowired
    private AuthorService service;

    @Test
    void findAllTest() throws RepositoryException, ServiceException {
        //given
        int expectedSize = 2;
        // when
        Mockito.when(repository.findAll()).thenReturn(Arrays.asList(new Author(), new Author()));
        int actualSize = service.findAll().size();
        //then
        assertEquals(expectedSize, actualSize);
    }

    @Test
    void deleteByIdTest() throws RepositoryException, ServiceException {
        //given && when
        int validId = 1;
        Mockito.when(repository.deleteById(validId)).thenReturn(true);
        //then
        assertTrue(service.deleteById(validId));
    }
}
