package com.flashsuppressor.java.lab.controller;

import com.flashsuppressor.java.lab.entity.dto.PublisherDTO;
import com.flashsuppressor.java.lab.service.PublisherService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
@RequestMapping("/publishers")
@RequiredArgsConstructor
public class PublisherController {

    private final PublisherService publisherService;

    @GetMapping(value = "/find/{id}")
    public ResponseEntity<PublisherDTO> find(@PathVariable(name = "id") int id) {
        final PublisherDTO publisher = publisherService.findById(id);

        return publisher != null
                ? new ResponseEntity<>(publisher, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping(value = "/find/all")
    public ResponseEntity<List<PublisherDTO>> findAll() {
        final List<PublisherDTO> publishers = publisherService.findAll();

        return publishers != null &&  !publishers.isEmpty()
                ? new ResponseEntity<>(publishers, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping(value = "/create")
    public ResponseEntity<PublisherDTO> create(@RequestBody PublisherDTO publisherDTO) {
        PublisherDTO publisher = publisherService.create(publisherDTO);

        return publisher != null
                ? new ResponseEntity<>(publisher, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PostMapping(value = "/create/all")
    public ResponseEntity<List<PublisherDTO>> createAll(@RequestBody List<PublisherDTO> publisherDTOList) {
        final List<PublisherDTO> publishers = publisherService.createAll(publisherDTOList);

        return publishers != null && !publishers.isEmpty()
                ? new ResponseEntity<>(publishers, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping(value = "/update")
    public ResponseEntity<PublisherDTO> update(@RequestBody PublisherDTO publisherDTO) {
        final PublisherDTO publisher = publisherService.update(publisherDTO);

        return publisher != null
                ? new ResponseEntity<>(HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }

    @DeleteMapping(value = "/delete/{id}")
    public ResponseEntity<PublisherDTO> delete(@PathVariable(name = "id") int id) {
        final boolean deleted = publisherService.deleteById(id);

        return deleted
                ? new ResponseEntity<>(HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }
}
