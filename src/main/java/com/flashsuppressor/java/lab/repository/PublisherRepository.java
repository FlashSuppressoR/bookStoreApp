package com.flashsuppressor.java.lab.repository;

import com.flashsuppressor.java.lab.entity.Publisher;

import java.sql.SQLException;
import java.util.List;

public interface PublisherRepository {

    Publisher findById(int id) throws SQLException;

    List<Publisher> findAll();

    Publisher create(Publisher publisher) throws SQLException;

    void createAll(List<Publisher> publisher);

    Publisher update(Publisher publisher) throws SQLException;

    boolean deleteById(int id) throws SQLException;
}
