package com.flashsuppressor.java.lab.repository.data;

import com.flashsuppressor.java.lab.entity.Publisher;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.JpaRepository;

@Primary
public interface PublisherRepository extends JpaRepository<Publisher, Integer> {

}
