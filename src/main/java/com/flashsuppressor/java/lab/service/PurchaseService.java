package com.flashsuppressor.java.lab.service;

import com.flashsuppressor.java.lab.entity.Purchase;
import com.flashsuppressor.java.lab.entity.dto.PurchaseDTO;
import com.flashsuppressor.java.lab.exception.ServiceException;

import java.util.List;

public interface PurchaseService {

    PurchaseDTO findById(int id) throws ServiceException;

    List<PurchaseDTO> findAll() throws ServiceException;

    void create(Purchase purchase) throws ServiceException;

    void createAll(List<Purchase> purchases) throws ServiceException;

    PurchaseDTO update(Purchase purchase) throws ServiceException;

    boolean deleteById(int id) throws ServiceException;
}
