package com.flashsuppressor.java.lab.repository;

import com.flashsuppressor.java.lab.entity.Book;

import java.util.List;

public interface BookRepository {

    Book findById(Long id);

    List<Book> findAll();

    Book create(Book book);

    void createAll(List<Book> books);

    Book update(Book book);

    boolean deleteById(Long id);
}
