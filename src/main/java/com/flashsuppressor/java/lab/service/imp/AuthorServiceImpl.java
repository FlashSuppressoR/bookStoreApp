package com.flashsuppressor.java.lab.service.imp;

import com.flashsuppressor.java.lab.entity.Author;
import com.flashsuppressor.java.lab.entity.dto.AuthorDTO;
import com.flashsuppressor.java.lab.repository.AuthorRepository;
import com.flashsuppressor.java.lab.service.AuthorService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class AuthorServiceImpl implements AuthorService {
    private final AuthorRepository repository;
    private final ModelMapper modelMapper;

    @Autowired
    public AuthorServiceImpl(@Qualifier("hibernateAuthorRepository")
                                     AuthorRepository repository, ModelMapper modelMapper) {
        this.repository = repository;
        this.modelMapper = modelMapper;
    }

    @Override
    @Transactional
    public AuthorDTO findById(int id) {
        Author author = repository.findById(id);

        return convertToAuthorDTO(author);

    }

    @Override
    @Transactional
    public List<AuthorDTO> findAll() {
        List<AuthorDTO> authorDTOs = new ArrayList<>();
        List<Author> authors = repository.findAll();
        if (authors.size() > 0) {
            authorDTOs = authors.stream().map(this::convertToAuthorDTO).collect(Collectors.toList());
        }

        return authorDTOs;
    }

    @Override
    @Transactional
    public AuthorDTO create(Author author) {
        AuthorDTO newAuthorDTO = null;
        Author newAuthor = repository.create(author);
        if (newAuthor != null) {
            newAuthorDTO = convertToAuthorDTO(newAuthor);
        } else {
            System.out.println("Object newAuthor == null");
        }

        return newAuthorDTO;
    }

    @Override
    @Transactional
    public void createAll(List<Author> authors) {
        for (Author author : authors) {
            repository.create(author);
        }
    }

    @Override
    @Transactional
    public AuthorDTO update(Author author) {
        AuthorDTO updatedAuthorDTO = null;
        Author updatedAuthor = repository.update(author);
        if (updatedAuthor != null) {
            updatedAuthorDTO = convertToAuthorDTO(updatedAuthor);
        }

        return updatedAuthorDTO;
    }

    @Override
    @Transactional
    public boolean deleteById(int id) {

        return repository.deleteById(id);
    }

    private AuthorDTO convertToAuthorDTO(Author author) {

        return modelMapper.map(author, AuthorDTO.class);
    }
}
