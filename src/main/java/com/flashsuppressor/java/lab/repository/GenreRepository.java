package com.flashsuppressor.java.lab.repository;

import com.flashsuppressor.java.lab.entity.Genre;
import com.flashsuppressor.java.lab.exception.RepositoryException;

import java.sql.SQLException;
import java.util.List;

public interface GenreRepository {

    Genre findById(int id) throws RepositoryException;

    List<Genre> findAll();

    void create(Genre genre) throws RepositoryException;

    void createAll(List<Genre> genres);

    Genre update(Genre genre) throws RepositoryException;

    boolean deleteById(int id) throws RepositoryException, SQLException;
}
