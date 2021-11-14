package com.flashsuppressor.java.lab.service;

import com.flashsuppressor.java.lab.entity.Purchase;
import com.flashsuppressor.java.lab.entity.dto.PurchaseDTO;

import java.util.List;

public interface PurchaseService {

    PurchaseDTO findById(int id);

    List<PurchaseDTO> findAll();

    void create(Purchase purchase);

    void createAll(List<Purchase> purchases);

    PurchaseDTO update(Purchase purchase);

    boolean deleteById(int id);
}
