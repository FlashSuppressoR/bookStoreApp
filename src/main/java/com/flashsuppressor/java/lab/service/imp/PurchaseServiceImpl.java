package com.flashsuppressor.java.lab.service.imp;

import com.flashsuppressor.java.lab.entity.Purchase;
import com.flashsuppressor.java.lab.entity.dto.PurchaseDTO;
import com.flashsuppressor.java.lab.exception.RepositoryException;
import com.flashsuppressor.java.lab.exception.ServiceException;
import com.flashsuppressor.java.lab.repository.PurchaseRepository;
import com.flashsuppressor.java.lab.service.PurchaseService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PurchaseServiceImpl implements PurchaseService {
    private final PurchaseRepository repository;
    private final ModelMapper modelMapper;

    @Autowired
    public PurchaseServiceImpl(@Qualifier("hibernatePurchaseRepository")
                                        PurchaseRepository repository, ModelMapper modelMapper) {
        this.repository = repository;
        this.modelMapper = modelMapper;
    }

    @Override
    public PurchaseDTO findById(int id) throws ServiceException {
        PurchaseDTO purchaseDTO = null;
        try{
            Purchase purchase = repository.findById(1);
            purchaseDTO = convertToPurchaseDTO(purchase);
        }
        catch (RepositoryException ex){
            throw new ServiceException(ex.getMessage());
        }
        return purchaseDTO;
    }

    @Override
    public List<PurchaseDTO> findAll() throws ServiceException {
        List<PurchaseDTO> purchaseDTOs = new ArrayList<>();
        List<Purchase> purchases = repository.findAll();
        if (purchases != null && purchases.size() > 0) {
            purchaseDTOs = purchases.stream().map(this::convertToPurchaseDTO).collect(Collectors.toList());
        }
        return purchaseDTOs;
    }

    @Override
    public void create(Purchase purchase) throws ServiceException {
        try {
            repository.create(purchase);
        } catch (RepositoryException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void createAll(List<Purchase> purchases) throws ServiceException {
        try {
            for (Purchase purchase : purchases){
                repository.create(purchase);
            }
        } catch (RepositoryException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public PurchaseDTO update(Purchase purchase) throws ServiceException {
        PurchaseDTO updatedPurchaseDTO = null;
        try {
            Purchase updatedPurchase = repository.update(purchase);
            if (updatedPurchase != null) {
                updatedPurchaseDTO = convertToPurchaseDTO(updatedPurchase);
            }
        } catch (RepositoryException ex) {
            ex.printStackTrace();
        }
        return updatedPurchaseDTO;
    }

    @Override
    public boolean deleteById(int id) throws ServiceException {
        boolean result;
        try {
            result = repository.deleteById(id);
        } catch (RepositoryException | SQLException ex) {
            throw new ServiceException(ex.getMessage());
        }
        return result;
    }

    private PurchaseDTO convertToPurchaseDTO(Purchase purchase) {
        return modelMapper.map(purchase, PurchaseDTO.class);
    }
}
