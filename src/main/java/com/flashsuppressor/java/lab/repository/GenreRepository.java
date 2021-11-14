package com.flashsuppressor.java.lab.repository;

import com.flashsuppressor.java.lab.entity.Genre;
import java.util.List;

public interface GenreRepository {

    Genre findById(int id);

    List<Genre> findAll();

    void create(Genre genre);

    void createAll(List<Genre> genres);

    Genre update(Genre genre);

    boolean deleteById(int id);
}
