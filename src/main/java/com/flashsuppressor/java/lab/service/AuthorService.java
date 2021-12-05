package com.flashsuppressor.java.lab.service;

import com.flashsuppressor.java.lab.service.dto.AuthorDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface AuthorService {

    AuthorDTO findById(int id);

    Page<AuthorDTO> findAll(Pageable pageable);

    AuthorDTO create(AuthorDTO authorDTO);

    List<AuthorDTO> createAll(List<AuthorDTO> authorDTOs);

    AuthorDTO update(AuthorDTO authorDTO);

    boolean deleteById(int id);
}
