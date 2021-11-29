package com.flashsuppressor.java.lab.repository.data;

import com.flashsuppressor.java.lab.entity.Book;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.JpaRepository;

@Primary
public interface BookRepository extends JpaRepository<Book, Long> {

    Boolean deleteById(long id);
}
