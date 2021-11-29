package com.flashsuppressor.java.lab.controller;

import com.flashsuppressor.java.lab.entity.dto.CartDTO;
import com.flashsuppressor.java.lab.service.CartService;
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
@RequestMapping("/carts")
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;

    @GetMapping(value = "find/{id}")
    public ResponseEntity<CartDTO> find(@PathVariable(name = "id") int id) {
        final CartDTO cart = cartService.findById(id);

        return cart != null
                ? new ResponseEntity<>(cart, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping(value = "/find/all")
    public ResponseEntity<List<CartDTO>> findAll() {
        final List<CartDTO> carts = cartService.findAll();

        return carts != null && !carts.isEmpty()
                ? new ResponseEntity<>(carts, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping(value = "/create")
    public ResponseEntity<CartDTO> create(@RequestBody CartDTO cartDTO) {
        final CartDTO cart = cartService.create(cartDTO);

        return cart != null
                ? new ResponseEntity<>(cart, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping(value = "/update")
    public ResponseEntity<CartDTO> update(@RequestBody CartDTO cartDTO) {
        final CartDTO cart = cartService.update(cartDTO);

        return cart != null
                ? new ResponseEntity<>(HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }

    @DeleteMapping(value = "/delete/{id}")
    public ResponseEntity<CartDTO> delete(@PathVariable(name = "id") int id) {
        final boolean deleted = cartService.deleteById(id);

        return deleted
                ? new ResponseEntity<>(HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }
}
