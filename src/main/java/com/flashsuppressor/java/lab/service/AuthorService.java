package com.flashsuppressor.java.lab.service;

import com.flashsuppressor.java.lab.entity.Author;
import com.flashsuppressor.java.lab.entity.dto.AuthorDTO;
import com.flashsuppressor.java.lab.exception.ServiceException;


import java.util.List;

public interface AuthorService {

    AuthorDTO findById(int id) throws ServiceException;

    List<AuthorDTO> findAll() throws ServiceException;

    AuthorDTO create(Author author) throws ServiceException;

    void createAll(List<Author> authors) throws ServiceException;

    AuthorDTO update(Author author) throws ServiceException;

    boolean deleteById(int id) throws ServiceException;
}
