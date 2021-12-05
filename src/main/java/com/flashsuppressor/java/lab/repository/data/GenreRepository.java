package com.flashsuppressor.java.lab.repository.data;

import com.flashsuppressor.java.lab.entity.Genre;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.JpaRepository;

@Primary
public interface GenreRepository extends JpaRepository<Genre, Integer> {

}
