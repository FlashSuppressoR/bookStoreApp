package com.flashsuppressor.java.lab.service.imp;

import com.flashsuppressor.java.lab.entity.Genre;
import com.flashsuppressor.java.lab.entity.dto.GenreDTO;
import com.flashsuppressor.java.lab.exception.RepositoryException;
import com.flashsuppressor.java.lab.exception.ServiceException;
import com.flashsuppressor.java.lab.repository.GenreRepository;
import com.flashsuppressor.java.lab.service.GenreService;
import lombok.Setter;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class GenreServiceImpl implements GenreService {
    private final GenreRepository repository;
    private final ModelMapper modelMapper;

    @Autowired
    public GenreServiceImpl(@Qualifier("hibernateGenreRepository")
                                       GenreRepository repository, ModelMapper modelMapper) {
        this.repository = repository;
        this.modelMapper = modelMapper;
    }

    @Override
    public GenreDTO findById(int id) throws ServiceException {
        GenreDTO genreDTO = null;
        try{
            Genre genre = repository.findById(1);
            genreDTO = convertToGenreDTO(genre);
        }
        catch (RepositoryException ex){
            throw new ServiceException(ex.getMessage());
        }
        return genreDTO;
    }

    @Override
    public List<GenreDTO> findAll() throws ServiceException{
        List<GenreDTO> genreDTOs = new ArrayList<>();
        List<Genre> genres = repository.findAll();
        if (genres != null && genres.size() > 0) {
            genreDTOs = genres.stream().map(this::convertToGenreDTO).collect(Collectors.toList());
        }
        return genreDTOs;
    }

    @Override
    public void create(Genre genre) throws ServiceException {
        try {
            repository.create(genre);
        } catch (RepositoryException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void createAll(List<Genre> genres) throws ServiceException {
        try {
            for (Genre genre : genres){
                repository.create(genre);
            }
        } catch (RepositoryException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public GenreDTO update(Genre genre) throws ServiceException {
        GenreDTO updatedGenreDTO = null;
        try {
            Genre updatedGenre = repository.update(genre);
            if (updatedGenre != null) {
                updatedGenreDTO = convertToGenreDTO(updatedGenre);
            }
        } catch (RepositoryException ex) {
            ex.printStackTrace();
        }
        return updatedGenreDTO;
    }

    @Override
    public boolean deleteById(int id) throws ServiceException {
        boolean result;
        try {
            result = repository.deleteById(id);
        } catch (RepositoryException | SQLException ex) {
            throw new ServiceException(ex.getMessage());
        }
        return result;
    }

    private GenreDTO convertToGenreDTO(Genre genre) {
        return modelMapper.map(genre, GenreDTO.class);
    }
}
