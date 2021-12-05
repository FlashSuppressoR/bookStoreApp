package com.flashsuppressor.java.lab.service;

import com.flashsuppressor.java.lab.service.dto.GenreDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface GenreService {
    GenreDTO findById(int id);

    Page<GenreDTO> findAll(Pageable pageable);

    GenreDTO create(GenreDTO genreDTO);

    List<GenreDTO> createAll(List<GenreDTO> genres);

    GenreDTO update(GenreDTO genreDTO);

    boolean deleteById(int id);
}
