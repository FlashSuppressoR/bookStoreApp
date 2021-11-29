package com.flashsuppressor.java.lab.repository.hibernate;

import com.flashsuppressor.java.lab.entity.Genre;
import java.util.List;

@Deprecated
public interface GenreRepository {

    Genre findById(int id);

    List<Genre> findAll();

    Genre create(Genre genre);

    List<Genre> createAll(List<Genre> genres);

    Genre update(Genre genre);

    boolean deleteById(int id);
}
