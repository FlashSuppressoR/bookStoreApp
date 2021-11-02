package com.flashsuppressor.java.lab.repository;

import com.flashsuppressor.java.lab.entity.Author;

import java.sql.SQLException;
import java.util.List;

public interface AuthorRepository {

    Author findById(int id) throws SQLException;

    List<Author> findAll();

    Author create(Author author) throws SQLException;

    void createAll(List<Author> authors) throws SQLException;

    Author update(Author author) throws SQLException;

    boolean deleteById(int id) throws SQLException;



}
