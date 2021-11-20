package com.flashsuppressor.java.lab.service.impl;

import com.flashsuppressor.java.lab.entity.Purchase;
import com.flashsuppressor.java.lab.entity.dto.PurchaseDTO;
import com.flashsuppressor.java.lab.repository.PurchaseRepository;
import com.flashsuppressor.java.lab.service.PurchaseService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class PurchaseServiceImpl implements PurchaseService {
    private final PurchaseRepository repository;
    private final ModelMapper modelMapper;

    @Override
    @Transactional(readOnly=true)
    public PurchaseDTO findById(int id) {
        Purchase purchase = repository.findById(id);

        return convertToPurchaseDTO(purchase);
    }

    @Override
    @Transactional(readOnly=true)
    public List<PurchaseDTO> findAll() {
        List<PurchaseDTO> purchaseDTOs = new ArrayList<>();
        List<Purchase> purchases = repository.findAll();
        if (purchases != null && purchases.size() > 0) {
            purchaseDTOs = purchases.stream().map(this::convertToPurchaseDTO).collect(Collectors.toList());
        }

        return purchaseDTOs;
    }

    @Override
    @Transactional
    public void create(Purchase purchase) {
        repository.create(purchase);
    }

    @Override
    @Transactional
    public void createAll(List<Purchase> purchases) {
        for (Purchase purchase : purchases) {
            repository.create(purchase);
        }
    }

    @Override
    @Transactional
    public PurchaseDTO update(Purchase purchase) {
        PurchaseDTO updatedPurchaseDTO = null;
        Purchase updatedPurchase = repository.update(purchase);
        if (updatedPurchase != null) {
            updatedPurchaseDTO = convertToPurchaseDTO(updatedPurchase);
        }

        return updatedPurchaseDTO;
    }

    @Override
    @Transactional
    public boolean deleteById(int id) {

        return repository.deleteById(id);
    }

    private PurchaseDTO convertToPurchaseDTO(Purchase purchase) {

        return modelMapper.map(purchase, PurchaseDTO.class);
    }
}
