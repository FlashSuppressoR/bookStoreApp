package com.flashsuppressor.java.lab.repository;

import com.flashsuppressor.java.lab.entity.Publisher;

import java.util.List;

public interface PublisherRepository {

    Publisher findById(int id);

    List<Publisher> findAll();

    void create(Publisher publisher);

    void createAll(List<Publisher> publisher);

    Publisher update(Publisher publisher);

    boolean deleteById(int id);
}
