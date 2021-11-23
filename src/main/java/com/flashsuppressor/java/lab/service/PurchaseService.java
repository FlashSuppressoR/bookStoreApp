package com.flashsuppressor.java.lab.service;

import com.flashsuppressor.java.lab.entity.dto.PurchaseDTO;

import java.util.List;

public interface PurchaseService {

    PurchaseDTO findById(int id);

    List<PurchaseDTO> findAll();

    PurchaseDTO create(PurchaseDTO purchaseDTO);

    List<PurchaseDTO> createAll(List<PurchaseDTO> purchasesDTO);

    PurchaseDTO update(PurchaseDTO purchaseDTO);

    boolean deleteById(int id);
}
