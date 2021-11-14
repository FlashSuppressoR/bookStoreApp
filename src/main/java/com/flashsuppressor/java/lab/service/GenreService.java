package com.flashsuppressor.java.lab.service;

import com.flashsuppressor.java.lab.entity.Genre;
import com.flashsuppressor.java.lab.entity.dto.GenreDTO;

import java.util.List;

public interface GenreService {
    GenreDTO findById(int id);

    List<GenreDTO> findAll();

    void create(Genre genre);

    void createAll(List<Genre> genres);

    GenreDTO update(Genre genre);

    boolean deleteById(int id);
}
