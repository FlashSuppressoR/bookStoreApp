package com.flashsuppressor.java.lab.service.impl;

import com.flashsuppressor.java.lab.entity.Genre;
import com.flashsuppressor.java.lab.service.dto.GenreDTO;
import com.flashsuppressor.java.lab.repository.data.GenreRepository;
import com.flashsuppressor.java.lab.service.GenreService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
    private final Pageable pageable = PageRequest.of(1, 5, Sort.by("name"));

    @Override
    public GenreDTO findById(int id) {
        return convertToGenreDTO(repository.getById(id));
    }

    @Override
    @Transactional(readOnly=true)
    public Page<GenreDTO> findAll(Pageable pgb) {
        Page<Genre> pages = repository.findAll(pageable);

        return new PageImpl<>(pages.stream().map(this::convertToGenreDTO).collect(Collectors.toList()));
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
            genre.setName(genreDTO.getName());

            repository.flush();
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
        repository.deleteById(id);
        return repository.findById(id).isEmpty();
    }

    private Genre convertToGenre(GenreDTO genreDTO) {
        return modelMapper.map(genreDTO, Genre.class);
    }

    private GenreDTO convertToGenreDTO(Genre genre) {
        return modelMapper.map(genre, GenreDTO.class);
    }
}
