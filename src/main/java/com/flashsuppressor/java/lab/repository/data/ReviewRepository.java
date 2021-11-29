package com.flashsuppressor.java.lab.repository.data;

import com.flashsuppressor.java.lab.entity.Review;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.JpaRepository;

@Primary
public interface ReviewRepository extends JpaRepository<Review, Integer> {

    Boolean deleteById(int id);
}
