package com.flashsuppressor.java.lab.service;

import com.flashsuppressor.java.lab.service.dto.PublisherDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface PublisherService {

    PublisherDTO findById(int id);

    Page<PublisherDTO> findAll(Pageable pageable);

    PublisherDTO create(PublisherDTO publisherDTO);

    List<PublisherDTO> createAll(List<PublisherDTO> publisherDTOs);

    PublisherDTO update(PublisherDTO publisherDTO);

    boolean deleteById(int id);
}
