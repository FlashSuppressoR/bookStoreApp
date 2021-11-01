package com.flashsuppressor.java.lab.repository;

import com.flashsuppressor.java.lab.entity.Publisher;

import java.sql.SQLException;
import java.util.List;

public interface PublisherRepository {

    List<Publisher> findAll() throws SQLException;

    Publisher add(Publisher publisher) throws SQLException;

    Publisher findById(int id) throws SQLException;

    void addAll(List<Publisher> publisher) throws SQLException;

    boolean deleteById(int id) throws SQLException;
}
