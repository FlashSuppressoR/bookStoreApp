package com.flashsuppressor.java.lab.service.impl;

import com.flashsuppressor.java.lab.entity.Publisher;
import com.flashsuppressor.java.lab.entity.dto.PublisherDTO;
import com.flashsuppressor.java.lab.repository.data.PublisherRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
public class PublisherServiceImplTest {

    @InjectMocks
    private PublisherServiceImpl service;
    @Mock
    private PublisherRepository repository;
    @Mock
    private ModelMapper modelMapper;
    @Mock
    private List<PublisherDTO> mockPublishersList;

    @Test
    void findByIdTest() {
        int publisherID = 1;
        Publisher publisher = Publisher.builder().id(publisherID).name("Test Publisher").build();
        PublisherDTO expectedPublisherDTO = PublisherDTO.builder().id(publisherID).name("Test Publisher").build();

        when(repository.getById(publisherID)).thenReturn(publisher);
        when(modelMapper.map(publisher, PublisherDTO.class)).thenReturn(expectedPublisherDTO);
        PublisherDTO actualPublisherDTO = service.findById(publisherID);

        assertEquals(expectedPublisherDTO, actualPublisherDTO);
    }

    @Test
    void findAllTest() {
        int expectedSize = 2;
        Mockito.when(repository.findAll()).thenReturn(Arrays.asList(new Publisher(), new Publisher()));
        int actualSize = service.findAll().size();

        assertEquals(expectedSize, actualSize);
    }

    @Test
    void createTest() {
        //given
        PublisherDTO publisherDTO = PublisherDTO.builder().id(4).name("New Publisher").build();
        Publisher publisher = Publisher.builder().id(4).name("New Publisher").build();
        //when
        when(modelMapper.map(publisherDTO, Publisher.class)).thenReturn(publisher);
        when(modelMapper.map(publisher, PublisherDTO.class)).thenReturn(publisherDTO);
        when(repository.save(publisher)).thenReturn(publisher);
        PublisherDTO actualPublisherDTO = service.create(publisherDTO);
        //then
        assertAll(() -> assertEquals(publisher.getId(), actualPublisherDTO.getId()),
                () -> assertEquals(publisher.getName(), actualPublisherDTO.getName()));
    }

    @Test
    void createAllTest() {
        //given
        List<PublisherDTO> listDTO = new ArrayList<>() {{
            add(PublisherDTO.builder().id(4).name("First Publisher").build());
            add(PublisherDTO.builder().id(5).name("Second Publisher").build());
        }};
        when(mockPublishersList.get(0)).thenReturn(listDTO.get(0));
        when(mockPublishersList.get(1)).thenReturn(listDTO.get(1));
        List<PublisherDTO> createList = new ArrayList<>() {{
            add(mockPublishersList.get(0));
            add(mockPublishersList.get(1));
        }};
        List<PublisherDTO> publisherDTOList = service.createAll(listDTO);
        //then
        assertAll(() -> assertEquals(createList.get(0).getId(), publisherDTOList.get(0).getId()),
                () -> assertEquals(createList.get(0).getName(), publisherDTOList.get(0).getName()),
                () -> assertEquals(createList.get(1).getId(), publisherDTOList.get(1).getId()),
                () -> assertEquals(createList.get(1).getName(), publisherDTOList.get(1).getName()));
    }

    @Test
    void updateTest() {
        //given
        int publisherId = 1;
        String newName = "Updated Publisher";
        PublisherDTO publisherDTO = PublisherDTO.builder().id(publisherId).name(newName).build();
        Publisher publisher = Publisher.builder().id(publisherId).name(newName).build();
        //when
        when(repository.getById(publisherId)).thenReturn(publisher);
        when(modelMapper.map(publisher, PublisherDTO.class)).thenReturn(publisherDTO);
        when(repository.getById(publisherId)).thenReturn(publisher);
        PublisherDTO actualUpdatedPublisher = service.update(publisherDTO);
        // then
        assertAll(() -> assertEquals(publisherId, actualUpdatedPublisher.getId()),
                () -> assertEquals(newName, actualUpdatedPublisher.getName())
        );
    }

    @Test
    void deleteByIdTest() {
        int validId = 1;
        Mockito.when(repository.deleteById(validId)).thenReturn(true);

        assertTrue(service.deleteById(validId));
    }
}
