package com.flashsuppressor.java.lab.service.imp;
import com.flashsuppressor.java.lab.entity.Book;
import com.flashsuppressor.java.lab.entity.dto.BookDTO;
import com.flashsuppressor.java.lab.exception.RepositoryException;
import com.flashsuppressor.java.lab.exception.ServiceException;
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
    public BookDTO findById(Long id) throws ServiceException {
        BookDTO bookDTO = null;
        try{
            Book book = repository.findById(1L);
            bookDTO = convertToBookDTO(book);
        }
        catch (RepositoryException ex){
            throw new ServiceException(ex.getMessage());
        }
        return bookDTO;
    }

    @Override
    @Transactional
    public List<BookDTO> findAll() throws ServiceException{
        List<BookDTO> bookDTOs = new ArrayList<>();
        List<Book> contracts = null;
        try {
            contracts = repository.findAll();
        } catch (RepositoryException e) {
            e.printStackTrace();
        }
        if (contracts.size() > 0) {
            bookDTOs = contracts.stream().map(this::convertToBookDTO).collect(Collectors.toList());
        }
        return bookDTOs;
    }

    @Override
    @Transactional
    public void create(Book book) throws ServiceException {
        try {
            repository.create(book);
        } catch (RepositoryException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    @Transactional
    public void createAll(List<Book> books) {
        try {
            for (Book book : books){
                repository.create(book);
            }
        } catch (RepositoryException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    @Transactional
    public BookDTO update(Book book) throws ServiceException {
        BookDTO updatedBookDTO = null;
        try {
            Book updatedBook = repository.update(book);
            if (updatedBook != null) {
                updatedBookDTO = convertToBookDTO(updatedBook);
            }
        } catch (RepositoryException ex) {
            ex.printStackTrace();
        }
        return updatedBookDTO;
    }

    @Override
    @Transactional
    public boolean deleteById(Long id) throws ServiceException {
        boolean result;
        try {
            result = repository.deleteById(id);
        } catch (RepositoryException ex) {
            throw new ServiceException(ex.getMessage());
        }
        return result;
    }

    private BookDTO convertToBookDTO(Book book) {
        return modelMapper.map(book, BookDTO.class);
    }
}
