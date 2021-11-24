package com.flashsuppressor.java.lab.service.impl;

import com.flashsuppressor.java.lab.entity.Author;
import com.flashsuppressor.java.lab.entity.dto.AuthorDTO;
import com.flashsuppressor.java.lab.repository.AuthorRepository;
import com.flashsuppressor.java.lab.service.AuthorService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
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
    public AuthorDTO create(AuthorDTO authorDTO){
        Author newAuthor = repository.create(convertToAuthor(authorDTO));
        return convertToAuthorDTO(newAuthor);
    }

    @Override
    @Transactional
    public List<AuthorDTO> createAll(List<AuthorDTO> authorDTOs) {
        List<AuthorDTO> authorDTOList = new ArrayList<>();
        for (AuthorDTO newAuthorDTO : authorDTOs) {
            Author newAuthor = repository.create(convertToAuthor(newAuthorDTO));
            authorDTOList.add(convertToAuthorDTO(newAuthor));
        }
        return authorDTOList;
    }

    @Override
    @Transactional
    public AuthorDTO update(AuthorDTO authorDTO) {
        AuthorDTO newAuthorDTO = null;
        try {
            Author author = repository.findById(authorDTO.getId());
            if (authorDTO.getName() != null) {
                author.setName(authorDTO.getName());
            }
            newAuthorDTO = convertToAuthorDTO(author);
        }
        catch (Exception e){
            System.out.println("Can't update authorDTO");
        }
        return newAuthorDTO;
    }

    @Override
    @Transactional
    public boolean deleteById(int id) {
        return repository.deleteById(id);
    }

    private Author convertToAuthor(AuthorDTO authorDTO) {
        return modelMapper.map(authorDTO, Author.class);
    }

    private AuthorDTO convertToAuthorDTO(Author author) {
        return modelMapper.map(author, AuthorDTO.class);
    }
}
