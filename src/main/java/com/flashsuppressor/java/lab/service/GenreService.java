package com.flashsuppressor.java.lab.service;

import com.flashsuppressor.java.lab.entity.Genre;
import com.flashsuppressor.java.lab.entity.dto.GenreDTO;
import com.flashsuppressor.java.lab.exception.ServiceException;

import java.util.List;

public interface GenreService {
    GenreDTO findById(int id) throws ServiceException;

    List<GenreDTO> findAll() throws ServiceException;

    void create(Genre genre) throws ServiceException;

    void createAll(List<Genre> genres) throws ServiceException;

    GenreDTO update(Genre genre) throws ServiceException;

    boolean deleteById(int id) throws ServiceException;
}
