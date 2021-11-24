package com.flashsuppressor.java.lab.contoller;

import com.flashsuppressor.java.lab.entity.dto.PublisherDTO;
import com.flashsuppressor.java.lab.entity.dto.PurchaseDTO;
import com.flashsuppressor.java.lab.service.PurchaseService;
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
@RequestMapping("/purchases")
@RequiredArgsConstructor
public class PurchaseController {

    private final PurchaseService purchaseService;

    @GetMapping(value = "/find/{id}")
    public ResponseEntity<PurchaseDTO> find(@PathVariable(name = "id") int id) {
        final PurchaseDTO purchase = purchaseService.findById(id);

        return purchase != null
                ? new ResponseEntity<>(purchase, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping(value = "/find/all")
    public ResponseEntity<List<PurchaseDTO>> findAll() {
        final List<PurchaseDTO> purchases = purchaseService.findAll();

        return purchases != null &&  !purchases.isEmpty()
                ? new ResponseEntity<>(purchases, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping(value = "/create")
    public ResponseEntity<PurchaseDTO> create(@RequestBody PurchaseDTO purchaseDTO) {
        PurchaseDTO purchase = purchaseService.create(purchaseDTO);

        return purchase != null
                ? new ResponseEntity<>(purchase, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PostMapping(value = "/create/all")
    public ResponseEntity<List<PurchaseDTO>> createAll(@RequestBody List<PurchaseDTO> purchaseDTOList) {
        final List<PurchaseDTO> purchases = purchaseService.createAll(purchaseDTOList);

        return purchases != null && !purchases.isEmpty()
                ? new ResponseEntity<>(purchases, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping(value = "/update")
    public ResponseEntity<PublisherDTO> update(@RequestBody PurchaseDTO purchaseDTO) {
        final PurchaseDTO purchase = purchaseService.update(purchaseDTO);

        return purchase != null
                ? new ResponseEntity<>(HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }

    @DeleteMapping(value = "/delete/{id}")
    public ResponseEntity<PurchaseDTO> delete(@PathVariable(name = "id") int id) {
        final boolean deleted = purchaseService.deleteById(id);

        return deleted
                ? new ResponseEntity<>(HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }
}
