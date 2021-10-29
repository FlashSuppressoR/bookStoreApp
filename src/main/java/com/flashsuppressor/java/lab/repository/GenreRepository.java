package com.flashsuppressor.java.lab.repository;

import com.flashsuppressor.java.lab.entity.Genre;

import java.sql.SQLException;
import java.util.List;

public interface GenreRepository {

    List<Genre> findAll() throws SQLException;
    Genre add(Genre genre) throws SQLException;
    void addAll(List<Genre> genres) throws SQLException;
    Genre findById(int id) throws SQLException;
    boolean deleteById(int id) throws SQLException;
}
