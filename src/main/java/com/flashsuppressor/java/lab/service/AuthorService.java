package com.flashsuppressor.java.lab.service;

import com.flashsuppressor.java.lab.entity.Author;
import com.flashsuppressor.java.lab.entity.dto.AuthorDTO;

import java.util.List;

public interface AuthorService {

    AuthorDTO findById(int id);

    List<AuthorDTO> findAll();

    AuthorDTO create(Author author);

    void createAll(List<Author> authors);

    AuthorDTO update(Author author);

    boolean deleteById(int id);
}
