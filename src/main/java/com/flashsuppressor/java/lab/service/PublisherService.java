package com.flashsuppressor.java.lab.service;

import com.flashsuppressor.java.lab.entity.Publisher;
import com.flashsuppressor.java.lab.entity.dto.PublisherDTO;
import com.flashsuppressor.java.lab.exception.ServiceException;

import java.util.List;

public interface PublisherService {

    PublisherDTO findById(int id) throws ServiceException;

    List<PublisherDTO> findAll() throws ServiceException;

    void create(Publisher publisher) throws ServiceException;

    void createAll(List<Publisher> publisher) throws ServiceException;

    PublisherDTO update(Publisher publisher) throws ServiceException;

    boolean deleteById(int id) throws ServiceException;
}
