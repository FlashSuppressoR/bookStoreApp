package com.flashsuppressor.java.lab.contoller;

import com.flashsuppressor.java.lab.entity.dto.CustomerDTO;
import com.flashsuppressor.java.lab.service.CustomerService;
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
@RequestMapping("/customers")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;

    @GetMapping(value = "/find/{id}")
    public ResponseEntity<CustomerDTO> find(@PathVariable(name = "id") int id) {
        final CustomerDTO customer = customerService.findById(id);

        return customer != null
                ? new ResponseEntity<>(customer, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping(value = "/find/all")
    public ResponseEntity<List<CustomerDTO>> findAll() {
        final List<CustomerDTO> customers = customerService.findAll();

        return customers != null && !customers.isEmpty()
                ? new ResponseEntity<>(customers, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping(value = "/crerate")
    public ResponseEntity<CustomerDTO> create(@RequestBody CustomerDTO customerDTO) {
        final CustomerDTO customer = customerService.create(customerDTO);

        return customer != null
                ? new ResponseEntity<>(customer, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping(value = "/create/all")
    public ResponseEntity<CustomerDTO> update(@RequestBody CustomerDTO customerDTO) {
        final CustomerDTO customer = customerService.update(customerDTO);

        return customer != null
                ? new ResponseEntity<>(HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }

    @DeleteMapping(value = "/delete/{id}")
    public ResponseEntity<CustomerDTO> delete(@PathVariable(name = "id") int id) {
        final boolean deleted = customerService.deleteById(id);

        return deleted
                ? new ResponseEntity<>(HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }
}
