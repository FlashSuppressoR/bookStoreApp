package com.flashsuppressor.java.lab.controller;

import com.flashsuppressor.java.lab.entity.dto.GenreDTO;
import com.flashsuppressor.java.lab.service.GenreService;
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
@RequestMapping("/genres")
@RequiredArgsConstructor
public class GenreController {

    private final GenreService genreService;

    @GetMapping(value = "/find/{id}")
    public ResponseEntity<GenreDTO> find(@PathVariable(name = "id") int id) {
        final GenreDTO genre = genreService.findById(id);

        return genre != null
                ? new ResponseEntity<>(genre, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping(value = "/find/all")
    public ResponseEntity<List<GenreDTO>> findAll() {
        final List<GenreDTO> genres = genreService.findAll();

        return genres != null &&  !genres.isEmpty()
                ? new ResponseEntity<>(genres, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping(value = "/create")
    public ResponseEntity<GenreDTO> create(@RequestBody GenreDTO genreDTO) {
        GenreDTO genre = genreService.create(genreDTO);

        return genre != null
                ? new ResponseEntity<>(genre, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PostMapping(value = "/create/all")
    public ResponseEntity<List<GenreDTO>> createAll(@RequestBody List<GenreDTO> genreDTOList) {
        final List<GenreDTO> genres = genreService.createAll(genreDTOList);

        return genres != null && !genres.isEmpty()
                ? new ResponseEntity<>(genres, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping(value = "/update")
    public ResponseEntity<GenreDTO> update(@RequestBody GenreDTO genreDTO) {
        final GenreDTO genre = genreService.update(genreDTO);

        return genre != null
                ? new ResponseEntity<>(HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }

    @DeleteMapping(value = "/delete/{id}")
    public ResponseEntity<GenreDTO> delete(@PathVariable(name = "id") int id) {
        final boolean deleted = genreService.deleteById(id);

        return deleted
                ? new ResponseEntity<>(HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }
}
