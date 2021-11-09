package com.flashsuppressor.java.lab.repository;

import com.flashsuppressor.java.lab.entity.Book;
import com.flashsuppressor.java.lab.exception.RepositoryException;

import java.util.List;

public interface BookRepository {

    Book findById(Long id) throws RepositoryException;

    List<Book> findAll() throws RepositoryException;

    void create(Book book) throws RepositoryException;

    void createAll(List<Book> books) throws RepositoryException;

    Book update(Book book) throws RepositoryException;

    boolean deleteById(Long id) throws RepositoryException;
}
