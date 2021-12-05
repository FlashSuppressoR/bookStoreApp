package com.flashsuppressor.java.lab.service.impl;

import com.flashsuppressor.java.lab.entity.Customer;
import com.flashsuppressor.java.lab.entity.Purchase;
import com.flashsuppressor.java.lab.service.dto.CustomerDTO;
import com.flashsuppressor.java.lab.service.dto.PurchaseDTO;
import com.flashsuppressor.java.lab.repository.data.PurchaseRepository;
import com.flashsuppressor.java.lab.service.PurchaseService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
    private final Pageable pageable = PageRequest.of(1, 5, Sort.by("name"));

    @Override
    public PurchaseDTO findById(int id) {
        return convertToPurchaseDTO(repository.getById(id));
    }

    @Override
    @Transactional(readOnly=true)
    public Page<PurchaseDTO> findAll(Pageable pgb) {
        Page<Purchase> pages = repository.findAll(pageable);

        return new PageImpl<>(pages.stream().map(this::convertToPurchaseDTO).collect(Collectors.toList()));
    }

    @Override
    @Transactional
    public PurchaseDTO create(PurchaseDTO purchaseDTO) {
        Purchase newPurchase = repository.save(convertToPurchase(purchaseDTO));
        return convertToPurchaseDTO(newPurchase);
    }

    @Override
    @Transactional
    public List<PurchaseDTO> createAll(List<PurchaseDTO> purchases) {
        List<PurchaseDTO> purchaseDTOList = new ArrayList<>();
        for (PurchaseDTO newPurchaseDTO : purchases) {
            Purchase newPurchase = repository.save(convertToPurchase(newPurchaseDTO));
            purchaseDTOList.add(convertToPurchaseDTO(newPurchase));
        }
        return purchaseDTOList;
    }

    @Override
    @Transactional
    public PurchaseDTO update(PurchaseDTO purchaseDTO) {
        PurchaseDTO newPurchaseDTO = null;
        try {
            Purchase purchase = repository.getById(purchaseDTO.getId());
            purchase.setCustomer(convertToCustomer(purchaseDTO.getCustomerDTO()));
            purchase.setPurchaseTime(purchaseDTO.getPurchaseTime());

            repository.flush();
            newPurchaseDTO = convertToPurchaseDTO(purchase);
        }
        catch (Exception e){
            System.out.println("Can't update authorDTO");
        }
        return newPurchaseDTO;
    }

    @Override
    @Transactional
    public boolean deleteById(int id) {
        repository.deleteById(id);
        return repository.findById(id).isEmpty();
    }

    private Customer convertToCustomer(CustomerDTO customerDTO) {
        return modelMapper.map(customerDTO, Customer.class);
    }

    private Purchase convertToPurchase(PurchaseDTO purchaseDTO) {
        return modelMapper.map(purchaseDTO, Purchase.class);
    }

    private PurchaseDTO convertToPurchaseDTO(Purchase purchase) {
        return modelMapper.map(purchase, PurchaseDTO.class);
    }
}
