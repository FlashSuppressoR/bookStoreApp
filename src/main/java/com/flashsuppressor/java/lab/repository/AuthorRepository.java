package com.flashsuppressor.java.lab.repository;

import com.flashsuppressor.java.lab.entity.Author;

import java.util.List;

public interface AuthorRepository {

    Author findById(int id);

    List<Author> findAll();

    Author create(Author author);

    List<Author> createAll(List<Author> authors);

    Author update(Author author);

    boolean deleteById(int id);

}
