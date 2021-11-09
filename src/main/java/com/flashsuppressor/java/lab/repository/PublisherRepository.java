package com.flashsuppressor.java.lab.repository;

import com.flashsuppressor.java.lab.entity.Publisher;
import com.flashsuppressor.java.lab.exception.RepositoryException;

import java.sql.SQLException;
import java.util.List;

public interface PublisherRepository {

    Publisher findById(int id) throws RepositoryException;

    List<Publisher> findAll();

    void create(Publisher publisher) throws RepositoryException;

    void createAll(List<Publisher> publisher);

    Publisher update(Publisher publisher) throws RepositoryException;

    boolean deleteById(int id) throws RepositoryException, SQLException;
}
