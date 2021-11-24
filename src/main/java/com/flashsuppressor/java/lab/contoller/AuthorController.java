package com.flashsuppressor.java.lab.contoller;

import com.flashsuppressor.java.lab.entity.dto.AuthorDTO;
import com.flashsuppressor.java.lab.service.AuthorService;
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
@RequestMapping("/authors")
@RequiredArgsConstructor
public class AuthorController {

    private final AuthorService authorService;

    @GetMapping(value = "/find/{id}")
    public ResponseEntity<AuthorDTO> find(@PathVariable(name = "id") int id) {
        final AuthorDTO author = authorService.findById(id);

        return author != null
                ? new ResponseEntity<>(author, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping(value = "/find/all")
    public ResponseEntity<List<AuthorDTO>> findAll() {
        final List<AuthorDTO> authors = authorService.findAll();

        return authors != null && !authors.isEmpty()
                ? new ResponseEntity<>(authors, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping(value = "/create")
    public ResponseEntity<AuthorDTO> create(@RequestBody AuthorDTO authorDTO) {
        AuthorDTO author = authorService.create(authorDTO);

        return author != null
                ? new ResponseEntity<>(author, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PostMapping(value = "/create/all")
    public ResponseEntity<List<AuthorDTO>> createAll(@RequestBody List<AuthorDTO> authorDTOList) {
        final List<AuthorDTO> authors = authorService.createAll(authorDTOList);

        return authors != null && !authors.isEmpty()
                ? new ResponseEntity<>(authors, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping(value = "/update")
    public ResponseEntity<AuthorDTO> update(@RequestBody AuthorDTO authorDTO) {
        final AuthorDTO author = authorService.update(authorDTO);

        return author != null
                ? new ResponseEntity<>(author, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }

    @DeleteMapping(value = "/delete/{id}")
    public ResponseEntity<Boolean> delete(@PathVariable(name = "id") int id) {
        final boolean deleted = authorService.deleteById(id);

        return deleted
                ? new ResponseEntity<>(deleted, HttpStatus.OK)
                : new ResponseEntity<>(deleted, HttpStatus.NOT_MODIFIED);
    }
}
