package com.flashsuppressor.java.lab.service;

import com.flashsuppressor.java.lab.entity.dto.PublisherDTO;

import java.util.List;

public interface PublisherService {

    PublisherDTO findById(int id);

    List<PublisherDTO> findAll();

    PublisherDTO create(PublisherDTO publisherDTO);

    List<PublisherDTO> createAll(List<PublisherDTO> publisherDTOs);

    PublisherDTO update(PublisherDTO publisherDTO);

    boolean deleteById(int id);
}
