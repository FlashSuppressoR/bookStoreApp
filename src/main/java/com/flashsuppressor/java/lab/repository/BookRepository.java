package com.flashsuppressor.java.lab.repository;

import com.flashsuppressor.java.lab.entity.Book;

import java.sql.SQLException;
import java.util.List;

public interface BookRepository {

    Book findById(Long id) throws SQLException;

    List<Book> findAll();

    Book create(Book book) throws SQLException;

    void createAll(List<Book> books);

    Book update(Book book) throws SQLException;

    boolean deleteById(int id) throws SQLException;
}
