package com.flashsuppressor.java.lab.repository.impl.JDBC;

import com.flashsuppressor.java.lab.entity.Publisher;
import com.flashsuppressor.java.lab.repository.BaseRepositoryTest;
import com.flashsuppressor.java.lab.repository.PublisherRepository;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class JDBCPublisherRepositoryTest extends BaseRepositoryTest {
    private final PublisherRepository publisherRepository;
    private final List<Publisher> expectedPublishers;

    public JDBCPublisherRepositoryTest() {
        super();
        publisherRepository = new JDBCPublisherRepository(getConnectionPool());
        expectedPublishers = new ArrayList<>() {{
            add(new Publisher(1, "Big Daddy"));
            add(new Publisher(2, "Minsk prod"));
            add(new Publisher(3, "New Town"));
        }};
    }

    @Test
    public void findAllTest() throws SQLException {
        //given
        //when
        List<Publisher> actualPublishers = publisherRepository.findAll();
        //then
        for (int i = 0; i < expectedPublishers.size(); i++) {
            assertPublisherEquals(expectedPublishers.get(i), actualPublishers.get(i));
        }
    }

    @Test
    public void addTest() throws SQLException {
        //given
        Publisher expectedPublisher = new Publisher(1, "Big Daddy");
        //when
        Publisher actualPublisher = publisherRepository.add(expectedPublisher);

        //then
        assertPublisherEquals(expectedPublisher, actualPublisher);
    }

    @Test
    public void addAllTest() throws SQLException {
        //given
        List<Publisher> expectedList = new ArrayList<>() {{
            add(new Publisher(4, "Ballads Writer"));
            add(new Publisher(5, "Third House"));
        }};
        //when
        List<Publisher> actualList = new ArrayList<>() {{
            add(new Publisher(null, "Ballads Writer"));
            add(new Publisher(null, "Third House"));
        }};
        publisherRepository.addAll(actualList);
        //then
        for (int i = 0; i < expectedList.size(); i++) {
            assertPublisherEquals(expectedList.get(i), actualList.get(i));
        }
    }

    @Test
    public void deleteByIdTest() throws SQLException {
        //when
        int publisherId = 1;
        //then
        assertTrue(publisherRepository.deleteById(publisherId));
    }

    private void assertPublisherEquals(Publisher expectedPublisher, Publisher actualPublisher) {
        assertEquals(expectedPublisher.getId(), actualPublisher.getId());
        assertEquals(expectedPublisher.getName(), actualPublisher.getName());
    }
}