package com.flashsuppressor.java.lab.service.imp;

import com.flashsuppressor.java.lab.entity.Genre;
import com.flashsuppressor.java.lab.entity.dto.GenreDTO;
import com.flashsuppressor.java.lab.repository.GenreRepository;
import com.flashsuppressor.java.lab.service.GenreService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class GenreServiceImpl implements GenreService {
    private final GenreRepository repository;
    private final ModelMapper modelMapper;

    @Autowired
    public GenreServiceImpl(@Qualifier("genreRepositoryImpl")
                                    GenreRepository repository, ModelMapper modelMapper) {
        this.repository = repository;
        this.modelMapper = modelMapper;
    }

    @Override
    @Transactional(readOnly=true)
    public GenreDTO findById(int id) {
        Genre genre = repository.findById(id);

        return convertToGenreDTO(genre);
    }

    @Override
    @Transactional(readOnly=true)
    public List<GenreDTO> findAll() {
        List<GenreDTO> genreDTOs = new ArrayList<>();
        List<Genre> genres = repository.findAll();
        if (genres != null && genres.size() > 0) {
            genreDTOs = genres.stream().map(this::convertToGenreDTO).collect(Collectors.toList());
        }

        return genreDTOs;
    }

    @Override
    @Transactional
    public void create(Genre genre) {
        repository.create(genre);
    }

    @Override
    @Transactional
    public void createAll(List<Genre> genres) {
        for (Genre genre : genres) {
            repository.create(genre);
        }
    }

    @Override
    @Transactional
    public GenreDTO update(Genre genre) {
        GenreDTO updatedGenreDTO = null;
        Genre updatedGenre = repository.update(genre);
        if (updatedGenre != null) {
            updatedGenreDTO = convertToGenreDTO(updatedGenre);
        }

        return updatedGenreDTO;
    }

    @Override
    @Transactional
    public boolean deleteById(int id) {

        return repository.deleteById(id);
    }

    private GenreDTO convertToGenreDTO(Genre genre) {

        return modelMapper.map(genre, GenreDTO.class);
    }
}
