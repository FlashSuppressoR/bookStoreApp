package com.flashsuppressor.java.lab.repository;

import com.flashsuppressor.java.lab.entity.Book;

import java.sql.SQLException;
import java.util.List;

public interface BookRepository {

    List<Book> findAll() throws SQLException;

    Book add(Book book) throws SQLException;

    void addAll(List<Book> books) throws SQLException;

    Book findById(Long id) throws SQLException;

    boolean deleteById(int id) throws SQLException;

    Book update(Book book) throws SQLException;
}
