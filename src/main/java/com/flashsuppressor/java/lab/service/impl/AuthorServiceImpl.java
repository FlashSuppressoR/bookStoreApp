package com.flashsuppressor.java.lab.service.impl;

import com.flashsuppressor.java.lab.entity.Author;
import com.flashsuppressor.java.lab.service.dto.AuthorDTO;
import com.flashsuppressor.java.lab.repository.data.AuthorRepository;
import com.flashsuppressor.java.lab.service.AuthorService;
import lombok.RequiredArgsConstructor;
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
@RequiredArgsConstructor
public class AuthorServiceImpl implements AuthorService {
    private final AuthorRepository repository;
    private final ModelMapper modelMapper;
    private final Pageable pageable = PageRequest.of(0, 5, Sort.by("name"));

    @Override
    public AuthorDTO findById(int id) {
        return convertToAuthorDTO(repository.getById(id));
    }

    @Override
    @Transactional(readOnly = true)
    public Page<AuthorDTO> findAll(Pageable pgb) {
        Page<Author> pages = repository.findAll(pageable);

        return new PageImpl<>(pages.stream().map(this::convertToAuthorDTO).collect(Collectors.toList()));
    }

    @Override
    @Transactional
    public AuthorDTO create(AuthorDTO authorDTO) {
        Author newAuthor = repository.save(convertToAuthor(authorDTO));
        return convertToAuthorDTO(newAuthor);
    }

    @Override
    @Transactional
    public List<AuthorDTO> createAll(List<AuthorDTO> authorDTOs) {
        List<AuthorDTO> authorDTOList = new ArrayList<>();
        for (AuthorDTO newAuthorDTO : authorDTOs) {
            Author newAuthor = repository.saveAndFlush(convertToAuthor(newAuthorDTO));
            authorDTOList.add(convertToAuthorDTO(newAuthor));
        }
        return authorDTOList;
    }

    @Override
    @Transactional
    public AuthorDTO update(AuthorDTO authorDTO) {
        Author author = repository.getById(authorDTO.getId());
        author.setName(authorDTO.getName());

        repository.flush();

        return convertToAuthorDTO(author);
    }

    @Override
    @Transactional
    public boolean deleteById(int id) {
        repository.deleteById(id);
        return repository.findById(id).isEmpty();
    }

    private Author convertToAuthor(AuthorDTO authorDTO) {
        return modelMapper.map(authorDTO, Author.class);
    }

    private AuthorDTO convertToAuthorDTO(Author author) {
        return modelMapper.map(author, AuthorDTO.class);
    }
}
