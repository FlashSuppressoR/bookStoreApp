package com.flashsuppressor.java.lab.service.impl;

import com.flashsuppressor.java.lab.entity.Publisher;
import com.flashsuppressor.java.lab.service.dto.PublisherDTO;
import com.flashsuppressor.java.lab.repository.data.PublisherRepository;
import com.flashsuppressor.java.lab.service.PublisherService;
import lombok.AllArgsConstructor;
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
@AllArgsConstructor
public class PublisherServiceImpl implements PublisherService {
    private final PublisherRepository repository;
    private final ModelMapper modelMapper;
    private final Pageable pageable = PageRequest.of(0, 5, Sort.by("name"));

    @Override
    public PublisherDTO findById(int id) {
        return convertToPublisherDTO(repository.getById(id));
    }

    @Override
    @Transactional(readOnly=true)
    public Page<PublisherDTO> findAll(Pageable pgb) {
        Page<Publisher> pages = repository.findAll(pageable);

        return new PageImpl<>(pages.stream().map(this::convertToPublisherDTO).collect(Collectors.toList()));
    }

    @Override
    @Transactional
    public PublisherDTO create(PublisherDTO publisherDTO) {
       Publisher newPublisher = repository.save(convertToPublisher(publisherDTO));
        return convertToPublisherDTO(newPublisher);
    }

    @Override
    @Transactional
    public List<PublisherDTO> createAll(List<PublisherDTO> publishers) {
        List<PublisherDTO> publisherDTOList = new ArrayList<>();
        for (PublisherDTO newPublisherDTO : publishers) {
            Publisher newPublisher = repository.save(convertToPublisher(newPublisherDTO));
            publisherDTOList.add(convertToPublisherDTO(newPublisher));
        }
        return publisherDTOList;
    }

    @Override
    @Transactional
    public PublisherDTO update(PublisherDTO publisherDTO) {
        PublisherDTO newPublisherDTO = null;
        try {
            Publisher publisher = repository.getById(publisherDTO.getId());
            publisher.setName(publisherDTO.getName());

            repository.flush();
            newPublisherDTO = convertToPublisherDTO(publisher);
        }
        catch (Exception e){
            System.out.println("Can't update publisherDTO");
        }
        return newPublisherDTO;
    }

    @Override
    @Transactional
    public boolean deleteById(int id) {
        repository.deleteById(id);
        return repository.findById(id).isEmpty();
    }

    private Publisher convertToPublisher(PublisherDTO publisherDTO) {
        return modelMapper.map(publisherDTO, Publisher.class);
    }

    private PublisherDTO convertToPublisherDTO(Publisher publisher) {
        return modelMapper.map(publisher, PublisherDTO.class);
    }
}
