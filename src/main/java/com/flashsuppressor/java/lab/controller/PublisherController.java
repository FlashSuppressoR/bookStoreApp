package com.flashsuppressor.java.lab.controller;

import com.flashsuppressor.java.lab.service.dto.PublisherDTO;
import com.flashsuppressor.java.lab.service.PublisherService;
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
@RequestMapping("/publishers")
@RequiredArgsConstructor
public class PublisherController {

    private final PublisherService publisherService;
    private final Pageable pageable = PageRequest.of(1, 5);

    @GetMapping(value = "/{id}")
    @PreAuthorize("hasAuthority('permission:reed')")
    public ResponseEntity<PublisherDTO> find(@PathVariable(name = "id") int id) {
        final PublisherDTO publisher = publisherService.findById(id);

        return publisher != null
                ? new ResponseEntity<>(publisher, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping(value = "/all")
    @PreAuthorize("hasAuthority('permission:reed')")
    public ResponseEntity<Page<PublisherDTO>> findAll() {
        final Page<PublisherDTO> publishers = publisherService.findAll(pageable);

        return publishers != null &&  !publishers.isEmpty()
                ? new ResponseEntity<>(publishers, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping(value = "/")
    @PreAuthorize("hasAuthority('permission:write')")
    public ResponseEntity<PublisherDTO> create(@RequestBody PublisherDTO publisherDTO) {
        PublisherDTO publisher = publisherService.create(publisherDTO);

        return publisher != null
                ? new ResponseEntity<>(publisher, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PostMapping(value = "/all")
    @PreAuthorize("hasAuthority('permission:write')")
    public ResponseEntity<List<PublisherDTO>> createAll(@RequestBody List<PublisherDTO> publisherDTOList) {
        final List<PublisherDTO> publishers = publisherService.createAll(publisherDTOList);

        return publishers != null && !publishers.isEmpty()
                ? new ResponseEntity<>(publishers, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping(value = "/")
    @PreAuthorize("hasAuthority('permission:write')")
    public ResponseEntity<PublisherDTO> update(@RequestBody PublisherDTO publisherDTO) {
        final PublisherDTO publisher = publisherService.update(publisherDTO);

        return publisher != null
                ? new ResponseEntity<>(HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }

    @DeleteMapping(value = "/{id}")
    @PreAuthorize("hasAuthority('permission:write')")
    public ResponseEntity<Boolean> delete(@PathVariable(name = "id") int id) {
        final boolean deleted = publisherService.deleteById(id);

        return deleted
                ? new ResponseEntity<>(deleted ,HttpStatus.OK)
                : new ResponseEntity<>(deleted ,HttpStatus.NOT_MODIFIED);
    }
}
