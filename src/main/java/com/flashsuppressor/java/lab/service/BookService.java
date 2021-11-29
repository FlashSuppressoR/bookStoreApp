package com.flashsuppressor.java.lab.service;

import com.flashsuppressor.java.lab.entity.dto.BookDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface BookService {

    BookDTO findById(Long id);

    Page<BookDTO> findAll(Pageable pageable);

    BookDTO create(BookDTO bookDTO);

    List<BookDTO> createAll(List<BookDTO> bookDTOS);

    BookDTO update(BookDTO bookDTO);

    boolean deleteById(long id);
}
