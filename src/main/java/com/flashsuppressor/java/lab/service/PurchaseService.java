package com.flashsuppressor.java.lab.service;

import com.flashsuppressor.java.lab.service.dto.PurchaseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface PurchaseService {

    PurchaseDTO findById(int id);

    Page<PurchaseDTO> findAll(Pageable pageable);

    PurchaseDTO create(PurchaseDTO purchaseDTO);

    List<PurchaseDTO> createAll(List<PurchaseDTO> purchasesDTO);

    PurchaseDTO update(PurchaseDTO purchaseDTO);

    boolean deleteById(int id);
}
