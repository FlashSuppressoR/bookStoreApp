package com.flashsuppressor.java.lab.service;

import com.flashsuppressor.java.lab.entity.Book;
import com.flashsuppressor.java.lab.entity.dto.BookDTO;

import java.util.List;

public interface BookService {

    BookDTO findById(Long id);

    List<BookDTO> findAll();

    void create(Book book);

    void createAll(List<Book> books);

    BookDTO update(Book book);

    boolean deleteById(Long id);
}
