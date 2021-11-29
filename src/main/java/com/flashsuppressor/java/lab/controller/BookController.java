package com.flashsuppressor.java.lab.controller;

import com.flashsuppressor.java.lab.entity.dto.BookDTO;
import com.flashsuppressor.java.lab.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
@RequestMapping("/books")
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;
    private final Pageable bookPageable = PageRequest.of(1, 5);

    @GetMapping(value = "/find/{id}")
    public ResponseEntity<BookDTO> find(@PathVariable(name = "id") Long id) {
        final BookDTO bookDTO = bookService.findById(id);

        return bookDTO != null
                ? new ResponseEntity<>(bookDTO, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping(value = "/find/all")
    public ResponseEntity<Page<BookDTO>> findAll() {
        final Page<BookDTO> books = bookService.findAll(bookPageable);

        return books != null &&  !books.isEmpty()
                ? new ResponseEntity<>(books, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping(value = "/create")
    public ResponseEntity<BookDTO> create(@RequestBody BookDTO bookDTO) {
        final BookDTO book = bookService.create(bookDTO);

        return book != null
                ? new ResponseEntity<>(book, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PostMapping(value = "/create/all")
    public ResponseEntity<List<BookDTO>> createAll(@RequestBody List<BookDTO> bookDTOList) {
        final List<BookDTO> books = bookService.createAll(bookDTOList);

        return books != null &&  !books.isEmpty()
                ? new ResponseEntity<>(books, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping(value = "/update")
    public ResponseEntity<BookDTO> update(@RequestBody BookDTO bookDTO) {
        final BookDTO book = bookService.update(bookDTO);

        return book != null
                ? new ResponseEntity<>(HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }

    @DeleteMapping(value = "/delete/{id}")
    public ResponseEntity<BookDTO> delete(@PathVariable(name = "id") Long id) {
        final boolean deleted = bookService.deleteById(id);

        return deleted
                ? new ResponseEntity<>(HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }
}
