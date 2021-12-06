package com.flashsuppressor.java.lab.controller;

import com.flashsuppressor.java.lab.service.dto.CustomerDTO;
import com.flashsuppressor.java.lab.service.CustomerService;
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
@RequestMapping("/customers")
@RequiredArgsConstructor
public class    CustomerController {

    private final CustomerService customerService;
    private final Pageable pageable = PageRequest.of(0, 5);

    @GetMapping(value = "/id/{id}")
    @PreAuthorize("hasAuthority('permission:reed')")
    public ResponseEntity<CustomerDTO> find(@PathVariable(name = "id") int id) {
        final CustomerDTO customer = customerService.findById(id);

        return customer != null
                ? new ResponseEntity<>(customer, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping(value = "/email/{email}")
    @PreAuthorize("hasAuthority('permission:reed')")
    public ResponseEntity<CustomerDTO> findByEmail(@PathVariable(name = "email") String email) {
        final CustomerDTO customer = customerService.findByEmail(email);

        return customer != null
                ? new ResponseEntity<>(customer, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping(value = "/all")
    @PreAuthorize("hasAuthority('permission:reed')")
    public ResponseEntity<Page<CustomerDTO>> findAll() {
        Page<CustomerDTO> customers = customerService.findAll(pageable);

        return customers != null && !customers.isEmpty()
                ? new ResponseEntity<>(customers, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping(value = "/")
    @PreAuthorize("hasAuthority('permission:write')")
    public ResponseEntity<CustomerDTO> create(@RequestBody CustomerDTO customerDTO) {
        final CustomerDTO customer = customerService.create(customerDTO);

        return customer != null
                ? new ResponseEntity<>(customer, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping(value = "/")
    @PreAuthorize("hasAuthority('permission:write')")
    public ResponseEntity<CustomerDTO> update(@RequestBody CustomerDTO customerDTO) {
        final CustomerDTO customer = customerService.update(customerDTO);

        return customer != null
                ? new ResponseEntity<>(HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }

    @DeleteMapping(value = "/{id}")
    @PreAuthorize("hasAuthority('permission:write')")
    public ResponseEntity<Boolean> delete(@PathVariable(name = "id") int id) {
        final boolean deleted = customerService.deleteById(id);

        return deleted
                ? new ResponseEntity<>(deleted, HttpStatus.OK)
                : new ResponseEntity<>(deleted, HttpStatus.NOT_MODIFIED);
    }
}
