package com.flashsuppressor.java.lab.repository;

import com.flashsuppressor.java.lab.entity.Author;

import java.sql.SQLException;
import java.util.List;

public interface AuthorRepository {

    Author add(Author author) throws SQLException;

    List<Author> findAll() throws SQLException;

    Author update(Author author) throws SQLException;

    boolean deleteById(int id) throws SQLException;

    Author findById(int id) throws SQLException;

    void addAll(List<Author> authors) throws SQLException;
}
