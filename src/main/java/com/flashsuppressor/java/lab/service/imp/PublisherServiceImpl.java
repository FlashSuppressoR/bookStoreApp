package com.flashsuppressor.java.lab.service.imp;

import com.flashsuppressor.java.lab.entity.Publisher;
import com.flashsuppressor.java.lab.entity.dto.PublisherDTO;
import com.flashsuppressor.java.lab.repository.PublisherRepository;
import com.flashsuppressor.java.lab.service.PublisherService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    public PublisherDTO findById(int id) {
        Publisher publisher = repository.findById(id);

        return convertToPublisherDTO(publisher);
    }

    @Override
    @Transactional
    public List<PublisherDTO> findAll() {
        List<PublisherDTO> publisherDTOs = new ArrayList<>();
        List<Publisher> publishers = repository.findAll();
        if (publishers != null && publishers.size() > 0) {
            publisherDTOs = publishers.stream().map(this::convertToPublisherDTO).collect(Collectors.toList());
        }

        return publisherDTOs;
    }

    @Override
    @Transactional
    public void create(Publisher publisher) {
        repository.create(publisher);
    }

    @Override
    @Transactional
    public void createAll(List<Publisher> publishers) {
        for (Publisher publisher : publishers) {
            repository.create(publisher);
        }
    }

    @Override
    @Transactional
    public PublisherDTO update(Publisher publisher) {
        PublisherDTO updatedPublisherDTO = null;
        Publisher updatedPublisher = repository.update(publisher);
        if (updatedPublisher != null) {
            updatedPublisherDTO = convertToPublisherDTO(updatedPublisher);
        }

        return updatedPublisherDTO;
    }

    @Override
    @Transactional
    public boolean deleteById(int id) {

        return repository.deleteById(id);
    }

    private PublisherDTO convertToPublisherDTO(Publisher publisher) {

        return modelMapper.map(publisher, PublisherDTO.class);
    }
}
