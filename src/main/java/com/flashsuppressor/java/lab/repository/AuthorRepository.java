package com.flashsuppressor.java.lab.repository;

import com.flashsuppressor.java.lab.entity.Author;
import com.flashsuppressor.java.lab.exception.RepositoryException;

import java.sql.SQLException;
import java.util.List;

public interface AuthorRepository {

    Author findById(int id) throws RepositoryException;

    List<Author> findAll() throws RepositoryException;

    Author create(Author author) throws RepositoryException;

    void createAll(List<Author> authors) throws RepositoryException;

    Author update(Author author) throws RepositoryException, SQLException;

    boolean deleteById(int id) throws RepositoryException;

}
