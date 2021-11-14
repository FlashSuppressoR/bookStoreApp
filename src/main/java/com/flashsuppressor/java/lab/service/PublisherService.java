package com.flashsuppressor.java.lab.service;

import com.flashsuppressor.java.lab.entity.Publisher;
import com.flashsuppressor.java.lab.entity.dto.PublisherDTO;

import java.util.List;

public interface PublisherService {

    PublisherDTO findById(int id);

    List<PublisherDTO> findAll();

    void create(Publisher publisher);

    void createAll(List<Publisher> publisher);

    PublisherDTO update(Publisher publisher);

    boolean deleteById(int id);
}
