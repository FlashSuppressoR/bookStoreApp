package com.flashsuppressor.java.lab.controller;

import com.flashsuppressor.java.lab.service.dto.PublisherDTO;
import com.flashsuppressor.java.lab.service.dto.PurchaseDTO;
import com.flashsuppressor.java.lab.service.PurchaseService;
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
@RequestMapping("/purchases")
@RequiredArgsConstructor
public class PurchaseController {

    private final PurchaseService purchaseService;
    private final Pageable pageable = PageRequest.of(0, 5);

    @GetMapping(value = "/{id}")
    @PreAuthorize("hasAuthority('permission:reed')")
    public ResponseEntity<PurchaseDTO> find(@PathVariable(name = "id") int id) {
        final PurchaseDTO purchase = purchaseService.findById(id);

        return purchase != null
                ? new ResponseEntity<>(purchase, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping(value = "/all")
    @PreAuthorize("hasAuthority('permission:reed')")
    public ResponseEntity<Page<PurchaseDTO>> findAll() {
        final Page<PurchaseDTO> purchases = purchaseService.findAll(pageable);

        return purchases != null &&  !purchases.isEmpty()
                ? new ResponseEntity<>(purchases, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping(value = "/")
    @PreAuthorize("hasAuthority('permission:write')")
    public ResponseEntity<PurchaseDTO> create(@RequestBody PurchaseDTO purchaseDTO) {
        PurchaseDTO purchase = purchaseService.create(purchaseDTO);

        return purchase != null
                ? new ResponseEntity<>(purchase, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PostMapping(value = "/all")
    @PreAuthorize("hasAuthority('permission:write')")
    public ResponseEntity<List<PurchaseDTO>> createAll(@RequestBody List<PurchaseDTO> purchaseDTOList) {
        final List<PurchaseDTO> purchases = purchaseService.createAll(purchaseDTOList);

        return purchases != null && !purchases.isEmpty()
                ? new ResponseEntity<>(purchases, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping(value = "/")
    @PreAuthorize("hasAuthority('permission:write')")
    public ResponseEntity<PublisherDTO> update(@RequestBody PurchaseDTO purchaseDTO) {
        final PurchaseDTO purchase = purchaseService.update(purchaseDTO);

        return purchase != null
                ? new ResponseEntity<>(HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }

    @DeleteMapping(value = "/{id}")
    @PreAuthorize("hasAuthority('permission:write')")
    public ResponseEntity<Boolean> delete(@PathVariable(name = "id") int id) {
        final boolean deleted = purchaseService.deleteById(id);

        return deleted
                ? new ResponseEntity<>(deleted, HttpStatus.OK)
                : new ResponseEntity<>(deleted, HttpStatus.NOT_MODIFIED);
    }
}
