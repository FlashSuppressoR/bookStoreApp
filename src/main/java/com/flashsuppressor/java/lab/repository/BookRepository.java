package com.flashsuppressor.java.lab.repository;

import com.flashsuppressor.java.lab.entity.Book;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;
import java.util.List;

public interface BookRepository {

    Book findById(Long id) throws SQLException;

    List<Book> findAll();

    void create(Book book) throws SQLException;

    void createAll(List<Book> books);

    Book update(Book book) throws SQLException;

    boolean deleteById(Long id) throws SQLException;
}
