package com.flashsuppressor.java.lab.repository.hibernate.impl;

import com.flashsuppressor.java.lab.entity.Publisher;
import com.flashsuppressor.java.lab.repository.BaseRepositoryTest;
import com.flashsuppressor.java.lab.repository.hibernate.PublisherRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Transactional
public class PublisherRepositoryImplTest extends BaseRepositoryTest {

    @Autowired
    private PublisherRepository publisherRepository;
    private final List<Publisher> expectedPublishers = new ArrayList<>() {{
        add(new Publisher(1, "Big Daddy"));
        add(new Publisher(2, "Minsk prod"));
        add(new Publisher(3, "New Town"));
    }};

    @Test
    public void findAllTest() {
        List<Publisher> actualPublishers = publisherRepository.findAll();

        for (int i = 0; i < expectedPublishers.size(); i++) {
            assertPublisherEquals(expectedPublishers.get(i), actualPublishers.get(i));
        }
    }

    @Test
    public void findByIdTest() {
        Publisher expected = expectedPublishers.get(0);
        Publisher actual = publisherRepository.findById(expected.getId());

        assertPublisherEquals(expected, actual);
    }

    @Test
    public void createTest() {
        Publisher expectedPublisher = new Publisher(1, "Big Daddy");
        publisherRepository.create(expectedPublisher);

        assertEquals(4, publisherRepository.findAll().size());
    }

    @Test
    public void createAllTest() {
        List<Publisher> expectedList = new ArrayList<>() {{
            add(new Publisher(4, "Ballads Writer"));
            add(new Publisher(5, "Third House"));
        }};
        List<Publisher> actualList = new ArrayList<>() {{
            add(new Publisher(null, "Ballads Writer"));
            add(new Publisher(null, "Third House"));
        }};
        publisherRepository.createAll(actualList);

        for (int i = 0; i < expectedList.size(); i++) {
            assertPublisherEquals(expectedList.get(i), actualList.get(i));
        }
    }

    @Test
    public void deleteByIdTest() {
        int publisherId = 1;

        assertTrue(publisherRepository.deleteById(publisherId));
    }

    private void assertPublisherEquals(Publisher expectedPublisher, Publisher actualPublisher) {
        assertEquals(expectedPublisher.getId(), actualPublisher.getId());
        assertEquals(expectedPublisher.getName(), actualPublisher.getName());
    }
}