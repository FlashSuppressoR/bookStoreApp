package com.flashsuppressor.java.lab.service;

import com.flashsuppressor.java.lab.entity.Book;
import com.flashsuppressor.java.lab.entity.dto.BookDTO;
import com.flashsuppressor.java.lab.exception.ServiceException;

import java.util.List;

public interface BookService {

    BookDTO findById(Long id) throws ServiceException;

    List<BookDTO> findAll() throws ServiceException;

    void create(Book book) throws ServiceException;

    void createAll(List<Book> books) throws ServiceException;

    BookDTO update(Book book) throws ServiceException;

    boolean deleteById(Long id) throws ServiceException;
}
