package com.flashsuppressor.java.lab.repository.data;

import com.flashsuppressor.java.lab.entity.Author;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.JpaRepository;

@Primary
public interface AuthorRepository extends JpaRepository<Author, Integer> {

}
