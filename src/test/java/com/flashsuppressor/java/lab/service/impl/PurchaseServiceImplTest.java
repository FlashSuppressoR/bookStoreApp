package com.flashsuppressor.java.lab.service.impl;

import com.flashsuppressor.java.lab.entity.Customer;
import com.flashsuppressor.java.lab.entity.Purchase;
import com.flashsuppressor.java.lab.repository.data.PurchaseRepository;
import com.flashsuppressor.java.lab.service.dto.PurchaseDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
public class PurchaseServiceImplTest {

    @InjectMocks
    private PurchaseServiceImpl service;
    @Mock
    private PurchaseRepository repository;
    @Mock
    private ModelMapper modelMapper;
    @Mock
    private List<PurchaseDTO> mockPurchasesList;

    private final Pageable pageable = PageRequest.of(1, 5, Sort.by("name"));

    @Test
    void findByIdTest() {
        //given
        int purchaseID = 1;
        Purchase purchase = Purchase.builder().id(purchaseID).customer(Customer.builder()
                .id(4).name("Alexis Sanchez").email("Sanchez@com").password("alex").build())
                .purchaseTime(Timestamp.valueOf("2007-09-10 00:00:00.0")).build();
        PurchaseDTO expectedPurchaseDTO = PurchaseDTO.builder().id(purchaseID).customerId(4)
                .purchaseTime(Timestamp.valueOf("2007-09-10 00:00:00.0")).build();
        when(repository.getById(purchaseID)).thenReturn(purchase);
        when(modelMapper.map(purchase, PurchaseDTO.class)).thenReturn(expectedPurchaseDTO);
        //when
        PurchaseDTO actualPurchaseDTO = service.findById(purchaseID);
        //then
        assertEquals(expectedPurchaseDTO, actualPurchaseDTO);
    }

    @Test
    void findAllTest() {
        //given
        int expectedSize = 2;
        Mockito.when(repository.findAll()).thenReturn(Arrays.asList(new Purchase(), new Purchase()));
        //when
        int actualSize = service.findAll(pageable).getSize();
        //then
        assertEquals(expectedSize, actualSize);
    }

    @Test
    void createTest() {
        //given
        int purchaseID = 4;
        Purchase purchase = Purchase.builder().id(purchaseID).customer(Customer.builder()
                .id(4).name("Alexis Sanchez").email("Sanchez@com").password("alex").build())
                .purchaseTime(Timestamp.valueOf("2007-09-10 00:00:00.0")).build();
        PurchaseDTO expectedPurchaseDTO = PurchaseDTO.builder().id(purchaseID).customerId(4)
                .purchaseTime(Timestamp.valueOf("2007-09-10 00:00:00.0")).build();
        when(modelMapper.map(expectedPurchaseDTO, Purchase.class)).thenReturn(purchase);
        when(modelMapper.map(purchase, PurchaseDTO.class)).thenReturn(expectedPurchaseDTO);
        when(repository.save(purchase)).thenReturn(purchase);
        //when
        PurchaseDTO actualPurchaseDTO = service.create(expectedPurchaseDTO);
        //then
        assertAll(() -> assertEquals(expectedPurchaseDTO.getId(), actualPurchaseDTO.getId()),
                () -> assertEquals(expectedPurchaseDTO.getCustomerId(), actualPurchaseDTO.getCustomerId()),
                () -> assertEquals(expectedPurchaseDTO.getPurchaseTime(), actualPurchaseDTO.getPurchaseTime()));
    }

    @Test
    void createAllTest() {
        //given
        List<PurchaseDTO> listDTO = new ArrayList<>() {{
            add(PurchaseDTO.builder().id(4).customerId(4)
                    .purchaseTime(Timestamp.valueOf("2007-09-10 00:00:00.0")).build());
            add(PurchaseDTO.builder().id(5).customerId(5)
                    .purchaseTime(Timestamp.valueOf("2007-09-10 00:00:00.0")).build());
        }};
        when(mockPurchasesList.get(0)).thenReturn(listDTO.get(0));
        when(mockPurchasesList.get(1)).thenReturn(listDTO.get(1));
        //when
        List<PurchaseDTO> createList = new ArrayList<>() {{
            add(mockPurchasesList.get(0));
            add(mockPurchasesList.get(1));
        }};
        List<PurchaseDTO> purchaseDTOList = service.createAll(listDTO);
        //then
        assertAll(() -> assertEquals(createList.get(0).getId(), purchaseDTOList.get(0).getId()),
                () -> assertEquals(createList.get(0).getCustomerId(), purchaseDTOList.get(0).getCustomerId()),
                () -> assertEquals(createList.get(0).getPurchaseTime(), purchaseDTOList.get(0).getPurchaseTime()),
                () -> assertEquals(createList.get(1).getId(), purchaseDTOList.get(1).getId()),
                () -> assertEquals(createList.get(1).getCustomerId(), purchaseDTOList.get(1).getCustomerId()),
                () -> assertEquals(createList.get(1).getPurchaseTime(), purchaseDTOList.get(1).getPurchaseTime()));
    }

    @Test
    void updateTest() {
        //given
        int purchaseID = 1;
        int customerId = 4;
        Purchase purchase = Purchase.builder().id(purchaseID).customer(Customer.builder()
                .id(customerId).name("Alexis Sanchez").email("Sanchez@com").password("alex").build())
                .purchaseTime(Timestamp.valueOf("2007-09-10 00:00:00.0")).build();
        PurchaseDTO expectedPurchaseDTO = PurchaseDTO.builder().id(purchaseID).customerId(customerId)
                .purchaseTime(Timestamp.valueOf("2007-09-10 00:00:00.0")).build();
        when(repository.getById(purchaseID)).thenReturn(purchase);
        when(modelMapper.map(purchase, PurchaseDTO.class)).thenReturn(expectedPurchaseDTO);
        when(repository.getById(purchaseID)).thenReturn(purchase);
        //when
        PurchaseDTO actualUpdatedPurchase = service.update(expectedPurchaseDTO);
        // then
        assertAll(() -> assertEquals(purchaseID, actualUpdatedPurchase.getId()),
                () -> assertEquals(customerId, actualUpdatedPurchase.getCustomerId())
        );
    }

    @Test
    void deleteByIdTest() {
        //given
        int validId = 1;
        //when
        repository.deleteById(validId);
        //then
        assertTrue(repository.findById(validId).isEmpty());
    }
}
