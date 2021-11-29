package com.flashsuppressor.java.lab.service.impl;

import com.flashsuppressor.java.lab.entity.Customer;
import com.flashsuppressor.java.lab.entity.Purchase;
import com.flashsuppressor.java.lab.entity.dto.CustomerDTO;
import com.flashsuppressor.java.lab.entity.dto.PurchaseDTO;
import com.flashsuppressor.java.lab.repository.data.PurchaseRepository;
import com.flashsuppressor.java.lab.service.PurchaseService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
        return convertToPurchaseDTO(repository.getById(id));
    }

    @Override
    @Transactional(readOnly=true)
    public List<PurchaseDTO> findAll() {
        return repository.findAll().stream().map(this::convertToPurchaseDTO).collect(Collectors.toList());
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
        List<PurchaseDTO> purchaseDTOList = null;
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
            if (purchaseDTO.getCustomerDTO() != null) {
                purchase.setCustomer(convertToCustomer(purchaseDTO.getCustomerDTO()));
            }
            purchase.setPurchaseTime(purchaseDTO.getPurchaseTime());
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

        return repository.deleteById(id);
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
