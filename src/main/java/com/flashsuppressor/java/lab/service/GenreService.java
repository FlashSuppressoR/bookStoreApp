package com.flashsuppressor.java.lab.service;

import com.flashsuppressor.java.lab.entity.dto.GenreDTO;

import java.util.List;

public interface GenreService {
    GenreDTO findById(int id);

    List<GenreDTO> findAll();

    GenreDTO create(GenreDTO genreDTO);

    List<GenreDTO> createAll(List<GenreDTO> genres);

    GenreDTO update(GenreDTO genreDTO);

    boolean deleteById(int id);
}
