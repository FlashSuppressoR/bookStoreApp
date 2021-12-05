package com.flashsuppressor.java.lab.service;

import com.flashsuppressor.java.lab.service.dto.ReviewDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ReviewService {

    ReviewDTO findById(int id);

    Page<ReviewDTO> findAll(Pageable pageable);

    ReviewDTO create(ReviewDTO reviewDTO);

    List<ReviewDTO> createAll(List<ReviewDTO> reviews);

    ReviewDTO update(ReviewDTO reviewDTO);

    boolean deleteById(int id);
}
