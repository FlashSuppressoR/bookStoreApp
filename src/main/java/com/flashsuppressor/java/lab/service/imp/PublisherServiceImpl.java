package com.flashsuppressor.java.lab.service.imp;

import com.flashsuppressor.java.lab.entity.Publisher;
import com.flashsuppressor.java.lab.entity.dto.PublisherDTO;
import com.flashsuppressor.java.lab.exception.RepositoryException;
import com.flashsuppressor.java.lab.exception.ServiceException;
import com.flashsuppressor.java.lab.repository.PublisherRepository;
import com.flashsuppressor.java.lab.service.PublisherService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PublisherServiceImpl implements PublisherService {
    private final PublisherRepository repository;
    private final ModelMapper modelMapper;

    @Autowired
    public PublisherServiceImpl(@Qualifier("hibernatePublisherRepository")
                                    PublisherRepository repository, ModelMapper modelMapper) {
        this.repository = repository;
        this.modelMapper = modelMapper;
    }

    @Override
    @Transactional
    public PublisherDTO findById(int id) throws ServiceException {
        PublisherDTO publisherDTO = null;
        try{
            Publisher publisher = repository.findById(1);
            publisherDTO = convertToPublisherDTO(publisher);
        }
        catch (RepositoryException ex){
            throw new ServiceException(ex.getMessage());
        }
        return publisherDTO;
    }

    @Override
    @Transactional
    public List<PublisherDTO> findAll() throws ServiceException{
        List<PublisherDTO> publisherDTOs = new ArrayList<>();
        List<Publisher> publishers = repository.findAll();
        if (publishers != null && publishers.size() > 0) {
            publisherDTOs = publishers.stream().map(this::convertToPublisherDTO).collect(Collectors.toList());
        }
        return publisherDTOs;
    }

    @Override
    @Transactional
    public void create(Publisher publisher) throws ServiceException {
        try {
            repository.create(publisher);
        } catch (RepositoryException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    @Transactional
    public void createAll(List<Publisher> publishers) throws ServiceException {
        try {
            for (Publisher publisher : publishers){
                repository.create(publisher);
            }
        } catch (RepositoryException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    @Transactional
    public PublisherDTO update(Publisher publisher) throws ServiceException {
        PublisherDTO updatedPublisherDTO = null;
        try {
            Publisher updatedPublisher = repository.update(publisher);
            if (updatedPublisher != null) {
                updatedPublisherDTO = convertToPublisherDTO(updatedPublisher);
            }
        } catch (RepositoryException ex) {
            ex.printStackTrace();
        }
        return updatedPublisherDTO;
    }

    @Override
    @Transactional
    public boolean deleteById(int id) throws ServiceException {
        boolean result;
        try {
            result = repository.deleteById(id);
        } catch (RepositoryException | SQLException ex) {
            throw new ServiceException(ex.getMessage());
        }
        return result;
    }

    private PublisherDTO convertToPublisherDTO(Publisher publisher) {
        return modelMapper.map(publisher, PublisherDTO.class);
    }
}
