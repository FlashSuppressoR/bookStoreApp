package com.flashsuppressor.java.lab.repository.hibernate;

import com.flashsuppressor.java.lab.entity.Book;

import java.util.List;

@Deprecated
public interface BookRepository {

    Book findById(Long id);

    List<Book> findAll();

    Book create(Book book);

    List<Book> createAll(List<Book> books);

    Book update(Book book);

    boolean deleteById(Long id);
}
