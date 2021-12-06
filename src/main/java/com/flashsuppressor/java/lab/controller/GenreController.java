package com.flashsuppressor.java.lab.controller;

import com.flashsuppressor.java.lab.service.dto.GenreDTO;
import com.flashsuppressor.java.lab.service.GenreService;
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
@RequestMapping("/genres")
@RequiredArgsConstructor
public class GenreController {

    private final GenreService genreService;
    private final Pageable pageable = PageRequest.of(0, 5);

    @GetMapping(value = "/{id}")
    @PreAuthorize("hasAuthority('permission:reed')")
    public ResponseEntity<GenreDTO> find(@PathVariable(name = "id") int id) {
        final GenreDTO genre = genreService.findById(id);

        return genre != null
                ? new ResponseEntity<>(genre, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping(value = "/all")
    @PreAuthorize("hasAuthority('permission:reed')")
    public ResponseEntity<Page<GenreDTO>> findAll() {
        Page<GenreDTO> genres = genreService.findAll(pageable);

        return genres != null &&  !genres.isEmpty()
                ? new ResponseEntity<>(genres, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping(value = "/")
    @PreAuthorize("hasAuthority('permission:write')")
    public ResponseEntity<GenreDTO> create(@RequestBody GenreDTO genreDTO) {
        GenreDTO genre = genreService.create(genreDTO);

        return genre != null
                ? new ResponseEntity<>(genre, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PostMapping(value = "/all")
    @PreAuthorize("hasAuthority('permission:write')")
    public ResponseEntity<List<GenreDTO>> createAll(@RequestBody List<GenreDTO> genreDTOList) {
        final List<GenreDTO> genres = genreService.createAll(genreDTOList);

        return genres != null && !genres.isEmpty()
                ? new ResponseEntity<>(genres, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping(value = "/")
    @PreAuthorize("hasAuthority('permission:write')")
    public ResponseEntity<GenreDTO> update(@RequestBody GenreDTO genreDTO) {
        final GenreDTO genre = genreService.update(genreDTO);

        return genre != null
                ? new ResponseEntity<>(HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }

    @DeleteMapping(value = "/{id}")
    @PreAuthorize("hasAuthority('permission:write')")
    public ResponseEntity<Boolean> delete(@PathVariable(name = "id") int id) {
        final boolean deleted = genreService.deleteById(id);

        return deleted
                ? new ResponseEntity<>(deleted, HttpStatus.OK)
                : new ResponseEntity<>(deleted, HttpStatus.NOT_MODIFIED);
    }
}
