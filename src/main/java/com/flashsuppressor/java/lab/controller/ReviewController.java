package com.flashsuppressor.java.lab.controller;

import com.flashsuppressor.java.lab.service.dto.ReviewDTO;
import com.flashsuppressor.java.lab.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/reviews")
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;
    private final Pageable pageable = PageRequest.of(1, 5);

    @GetMapping(value = "/{id}")
    @PreAuthorize("hasAuthority('permission:reed')")
    public ResponseEntity<ReviewDTO> find(@PathVariable(name = "id") int id) {
        final ReviewDTO review = reviewService.findById(id);

        return review != null
                ? new ResponseEntity<>(review, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping(value = "/all")
    @PreAuthorize("hasAuthority('permission:reed')")
    public ResponseEntity<Page<ReviewDTO>> findAll() {
        final Page<ReviewDTO> reviews = reviewService.findAll(pageable);

        return reviews != null && !reviews.isEmpty()
                ? new ResponseEntity<>(reviews, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping(value = "/")
    @PreAuthorize("hasAuthority('permission:write')")
    public ResponseEntity<ReviewDTO> create(@RequestBody ReviewDTO reviewDTO) {
        ReviewDTO review = reviewService.create(reviewDTO);

        return review != null
                ? new ResponseEntity<>(review, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PostMapping(value = "/all")
    @PreAuthorize("hasAuthority('permission:write')")
    public ResponseEntity<List<ReviewDTO>> createAll(@RequestBody List<ReviewDTO> reviewDTOList) {
        final List<ReviewDTO> reviews = reviewService.createAll(reviewDTOList);

        return reviews != null && !reviews.isEmpty()
                ? new ResponseEntity<>(reviews, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping(value = "/")
    @PreAuthorize("hasAuthority('permission:write')")
    public ResponseEntity<ReviewDTO> update(@RequestBody ReviewDTO reviewDTO) {
        final ReviewDTO review = reviewService.update(reviewDTO);

        return review != null
                ? new ResponseEntity<>(HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }

    @DeleteMapping(value = "/{id}")
    @PreAuthorize("hasAuthority('permission:write')")
    public ResponseEntity<Boolean> delete(@PathVariable(name = "id") int id) {
        final boolean deleted = reviewService.deleteById(id);

        return deleted
                ? new ResponseEntity<>(deleted, HttpStatus.OK)
                : new ResponseEntity<>(deleted, HttpStatus.NOT_MODIFIED);
    }
}
