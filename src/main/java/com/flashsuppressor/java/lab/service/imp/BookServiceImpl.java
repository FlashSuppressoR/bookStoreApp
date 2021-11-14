package com.flashsuppressor.java.lab.service.imp;

import com.flashsuppressor.java.lab.entity.Book;
import com.flashsuppressor.java.lab.entity.dto.BookDTO;
import com.flashsuppressor.java.lab.repository.BookRepository;
import com.flashsuppressor.java.lab.service.BookService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookServiceImpl implements BookService {

    private final BookRepository repository;
    private final ModelMapper modelMapper;

    @Autowired
    public BookServiceImpl(@Qualifier("hibernateBookRepository")
                                   BookRepository repository, ModelMapper modelMapper) {
        this.repository = repository;
        this.modelMapper = modelMapper;
    }

    @Override
    @Transactional
    public BookDTO findById(Long id) {
        Book book = repository.findById(id);

        return convertToBookDTO(book);
    }

    @Override
    @Transactional
    public List<BookDTO> findAll() {
        List<BookDTO> bookDTOs = new ArrayList<>();
        List<Book> books = repository.findAll();
        if (books.size() > 0) {
            bookDTOs = books.stream().map(this::convertToBookDTO).collect(Collectors.toList());
        }

        return bookDTOs;
    }

    @Override
    @Transactional
    public void create(Book book) {
        repository.create(book);
    }

    @Override
    @Transactional
    public void createAll(List<Book> books) {
        for (Book book : books) {
            repository.create(book);
        }
    }

    @Override
    @Transactional
    public BookDTO update(Book book) {
        BookDTO updatedBookDTO = null;
        Book updatedBook = repository.update(book);
        if (updatedBook != null) {
            updatedBookDTO = convertToBookDTO(updatedBook);
        }

        return updatedBookDTO;
    }

    @Override
    @Transactional
    public boolean deleteById(Long id) {

        return repository.deleteById(id);
    }

    private BookDTO convertToBookDTO(Book book) {

        return modelMapper.map(book, BookDTO.class);
    }
}