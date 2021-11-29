package com.flashsuppressor.java.lab.service.impl;

import com.flashsuppressor.java.lab.entity.Genre;
import com.flashsuppressor.java.lab.entity.dto.GenreDTO;
import com.flashsuppressor.java.lab.repository.data.GenreRepository;
import com.flashsuppressor.java.lab.service.GenreService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class GenreServiceImpl implements GenreService {
    private final GenreRepository repository;
    private final ModelMapper modelMapper;

    @Override
    @Transactional(readOnly=true)
    public GenreDTO findById(int id) {
        return convertToGenreDTO(repository.getById(id));
    }

    @Override
    @Transactional(readOnly=true)
    public List<GenreDTO> findAll() {
        return repository.findAll().stream().map(this::convertToGenreDTO).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public GenreDTO create(GenreDTO genreDTO) {
        Genre newGenre = repository.save(convertToGenre(genreDTO));
        return convertToGenreDTO(newGenre);
    }

    @Override
    @Transactional
    public List<GenreDTO> createAll(List<GenreDTO> genres) {
        List<GenreDTO> genreDTOList = new ArrayList<>();
        for (GenreDTO newGenreDTO : genres) {
            Genre newGenre = repository.save(convertToGenre(newGenreDTO));
            genreDTOList.add(convertToGenreDTO(newGenre));
        }
        return genreDTOList;
    }

    @Override
    @Transactional
    public GenreDTO update(GenreDTO genreDTO) {
        GenreDTO newGenreDTO = null;
        try {
            Genre genre = repository.getById(genreDTO.getId());
            if (genreDTO.getName() != null) {
                genre.setName(genreDTO.getName());
            }
            newGenreDTO = convertToGenreDTO(genre);
        }
        catch (Exception e){
            System.out.println("Can't update genreDTO");
        }
        return newGenreDTO;
    }

    @Override
    @Transactional
    public boolean deleteById(int id) {
        return repository.deleteById(id);
    }

    private Genre convertToGenre(GenreDTO genreDTO) {
        return modelMapper.map(genreDTO, Genre.class);
    }

    private GenreDTO convertToGenreDTO(Genre genre) {
        return modelMapper.map(genre, GenreDTO.class);
    }
}
