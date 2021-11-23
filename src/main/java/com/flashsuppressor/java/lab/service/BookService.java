package com.flashsuppressor.java.lab.service;

import com.flashsuppressor.java.lab.entity.dto.BookDTO;

import java.util.List;

public interface BookService {

    BookDTO findById(Long id);

    List<BookDTO> findAll();

    BookDTO create(BookDTO bookDTO);

    List<BookDTO> createAll(List<BookDTO> bookDTOS);

    BookDTO update(BookDTO bookDTO);

    boolean deleteById(Long id);
}
