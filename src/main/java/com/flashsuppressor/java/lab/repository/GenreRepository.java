package com.flashsuppressor.java.lab.repository;

import com.flashsuppressor.java.lab.entity.Genre;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;
import java.util.List;

public interface GenreRepository {

    Genre findById(int id) throws SQLException;

    List<Genre> findAll();

    void create(Genre genre) throws SQLException;

    void createAll(List<Genre> genres);

    Genre update(Genre genre) throws SQLException;

    boolean deleteById(int id) throws SQLException;
}
