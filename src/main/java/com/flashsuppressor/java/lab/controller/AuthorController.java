package com.flashsuppressor.java.lab.controller;

import com.flashsuppressor.java.lab.service.dto.AuthorDTO;
import com.flashsuppressor.java.lab.service.AuthorService;
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
@RequestMapping("/authors")
@RequiredArgsConstructor
public class AuthorController {

    private final AuthorService authorService;
    private final Pageable pageable = PageRequest.of(0, 5);

    @GetMapping(value = "/{id}")
    @PreAuthorize("hasAuthority('permission:reed')")
    public ResponseEntity<AuthorDTO> find(@PathVariable(name = "id") int id) {
        AuthorDTO author = authorService.findById(id);

        return author != null
                ? new ResponseEntity<>(author, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping(value = "/all")
    @PreAuthorize("hasAuthority('permission:reed')")
    public ResponseEntity<Page<AuthorDTO>> findAll() {
        Page<AuthorDTO> authors = authorService.findAll(pageable);

        return authors != null && !authors.isEmpty()
                ? new ResponseEntity<>(authors, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping(value = "/")
    @PreAuthorize("hasAuthority('permission:write')")
    public ResponseEntity<AuthorDTO> create(@RequestBody AuthorDTO authorDTO) {
        AuthorDTO author = authorService.create(authorDTO);

        return author != null
                ? new ResponseEntity<>(author, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PostMapping(value = "/all")
    @PreAuthorize("hasAuthority('permission:write')")
    public ResponseEntity<List<AuthorDTO>> createAll(@RequestBody List<AuthorDTO> authorDTOList) {
        List<AuthorDTO> authors = authorService.createAll(authorDTOList);

        return authors != null && !authors.isEmpty()
                ? new ResponseEntity<>(authors, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping(value = "/")
    @PreAuthorize("hasAuthority('permission:write')")
    public ResponseEntity<AuthorDTO> update(@RequestBody AuthorDTO authorDTO) {
        AuthorDTO author = authorService.update(authorDTO);

        return author != null
                ? new ResponseEntity<>(author, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }

    @DeleteMapping(value = "/{id}")
    @PreAuthorize("hasAuthority('permission:write')")
    public ResponseEntity<Boolean> delete(@PathVariable(name = "id") int id) {
        boolean deleted = authorService.deleteById(id);

        return deleted
                ? new ResponseEntity<>(deleted, HttpStatus.OK)
                : new ResponseEntity<>(deleted, HttpStatus.NOT_MODIFIED);
    }
}
