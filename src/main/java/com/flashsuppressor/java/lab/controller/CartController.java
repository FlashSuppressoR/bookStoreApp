package com.flashsuppressor.java.lab.controller;

import com.flashsuppressor.java.lab.service.dto.CartDTO;
import com.flashsuppressor.java.lab.service.CartService;
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

@RestController
@RequestMapping("/carts")
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;
    private final Pageable pageable = PageRequest.of(0, 5);

    @GetMapping(value = "/{id}")
    @PreAuthorize("hasAuthority('permission:reed')")
    public ResponseEntity<CartDTO> find(@PathVariable(name = "id") int id) {
        final CartDTO cart = cartService.findById(id);

        return cart != null
                ? new ResponseEntity<>(cart, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping(value = "/all")
    @PreAuthorize("hasAuthority('permission:reed')")
    public ResponseEntity<Page<CartDTO>> findAll() {
        Page<CartDTO> carts = cartService.findAll(pageable);

        return carts != null && !carts.isEmpty()
                ? new ResponseEntity<>(carts, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping(value = "/")
    @PreAuthorize("hasAuthority('permission:write')")
    public ResponseEntity<CartDTO> create(@RequestBody CartDTO cartDTO) {
        final CartDTO cart = cartService.create(cartDTO);

        return cart != null
                ? new ResponseEntity<>(cart, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping(value = "/")
    @PreAuthorize("hasAuthority('permission:write')")
    public ResponseEntity<CartDTO> update(@RequestBody CartDTO cartDTO) {
        final CartDTO cart = cartService.update(cartDTO);

        return cart != null
                ? new ResponseEntity<>(HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }

    @DeleteMapping(value = "/{id}")
    @PreAuthorize("hasAuthority('permission:write')")
    public ResponseEntity<Boolean> delete(@PathVariable(name = "id") int id) {
        final boolean deleted = cartService.deleteById(id);

        return deleted
                ? new ResponseEntity<>(deleted, HttpStatus.OK)
                : new ResponseEntity<>(deleted, HttpStatus.NOT_MODIFIED);
    }
}
