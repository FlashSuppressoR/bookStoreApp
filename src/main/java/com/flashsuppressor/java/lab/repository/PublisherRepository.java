package com.flashsuppressor.java.lab.repository;

import com.flashsuppressor.java.lab.entity.Publisher;

import java.util.List;

public interface PublisherRepository {

    Publisher findById(int id);

    List<Publisher> findAll();

    Publisher create(Publisher publisher);

    List<Publisher> createAll(List<Publisher> publisher);

    Publisher update(Publisher publisher);

    boolean deleteById(int id);
}
