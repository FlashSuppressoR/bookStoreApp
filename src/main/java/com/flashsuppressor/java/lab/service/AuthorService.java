package com.flashsuppressor.java.lab.service;

import com.flashsuppressor.java.lab.entity.dto.AuthorDTO;

import java.util.List;

public interface AuthorService {

    AuthorDTO findById(int id);

    List<AuthorDTO> findAll();

    AuthorDTO create(AuthorDTO authorDTO);

    List<AuthorDTO> createAll(List<AuthorDTO> authorDTOs);

    AuthorDTO update(AuthorDTO authorDTO);

    boolean deleteById(int id);
}
