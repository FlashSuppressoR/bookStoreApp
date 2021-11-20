package com.flashsuppressor.java.lab.service.impl;

import com.flashsuppressor.java.lab.entity.Author;
import com.flashsuppressor.java.lab.entity.dto.AuthorDTO;
import com.flashsuppressor.java.lab.repository.AuthorRepository;
import com.flashsuppressor.java.lab.service.AuthorService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;


@Service
@AllArgsConstructor
public class AuthorServiceImpl implements AuthorService {
    private final AuthorRepository repository;
    private final ModelMapper modelMapper;

    @Override
    @Transactional(readOnly=true)
    public AuthorDTO findById(int id) {
        return convertToAuthorDTO(repository.findById(id));
    }

    @Override
    @Transactional(readOnly=true)
    public List<AuthorDTO> findAll() {
        return repository.findAll().stream().map(this::convertToAuthorDTO).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public AuthorDTO create(Author author){
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
