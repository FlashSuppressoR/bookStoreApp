package com.flashsuppressor.java.lab.service.impl;

import com.flashsuppressor.java.lab.entity.Customer;
import com.flashsuppressor.java.lab.entity.Purchase;
import com.flashsuppressor.java.lab.service.dto.CustomerDTO;
import com.flashsuppressor.java.lab.service.dto.PurchaseDTO;
import com.flashsuppressor.java.lab.repository.data.PurchaseRepository;
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
        PurchaseDTO expectedPurchaseDTO = PurchaseDTO.builder().id(purchaseID).customerDTO(CustomerDTO.builder()
                .id(4).name("Alexis Sanchez").email("Sanchez@com").password("alex").build())
                .purchaseTime(Timestamp.valueOf("2007-09-10 00:00:00.0")).build();
        //when
        when(repository.getById(purchaseID)).thenReturn(purchase);
        when(modelMapper.map(purchase, PurchaseDTO.class)).thenReturn(expectedPurchaseDTO);
        PurchaseDTO actualPurchaseDTO = service.findById(purchaseID);
        //then
        assertEquals(expectedPurchaseDTO, actualPurchaseDTO);
    }

    @Test
    void findAllTest() {
        //given
        int expectedSize = 2;
        //when
        Mockito.when(repository.findAll()).thenReturn(Arrays.asList(new Purchase(), new Purchase()));
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
        PurchaseDTO expectedPurchaseDTO = PurchaseDTO.builder().id(purchaseID).customerDTO(CustomerDTO.builder()
                .id(4).name("Alexis Sanchez").email("Sanchez@com").password("alex").build())
                .purchaseTime(Timestamp.valueOf("2007-09-10 00:00:00.0")).build();
        //when
        when(modelMapper.map(expectedPurchaseDTO, Purchase.class)).thenReturn(purchase);
        when(modelMapper.map(purchase, PurchaseDTO.class)).thenReturn(expectedPurchaseDTO);
        when(repository.save(purchase)).thenReturn(purchase);
        PurchaseDTO actualPurchaseDTO = service.create(expectedPurchaseDTO);
        //then
        assertAll(() -> assertEquals(expectedPurchaseDTO.getId(), actualPurchaseDTO.getId()),
                () -> assertEquals(expectedPurchaseDTO.getCustomerDTO(), actualPurchaseDTO.getCustomerDTO()),
                () -> assertEquals(expectedPurchaseDTO.getPurchaseTime(), actualPurchaseDTO.getPurchaseTime()));
    }

    @Test
    void createAllTest() {
        //given
        List<PurchaseDTO> listDTO = new ArrayList<>() {{
            add(PurchaseDTO.builder().id(4).customerDTO(CustomerDTO.builder()
                    .id(4).name("Alexis Sanchez").email("Sanchez@com").password("alex").build())
                    .purchaseTime(Timestamp.valueOf("2007-09-10 00:00:00.0")).build());
            add(PurchaseDTO.builder().id(5).customerDTO(CustomerDTO.builder()
                    .id(5).name("Alde Saeq").email("ez@com").password("aaex").build())
                    .purchaseTime(Timestamp.valueOf("2007-09-10 00:00:00.0")).build());
        }};
        //when
        when(mockPurchasesList.get(0)).thenReturn(listDTO.get(0));
        when(mockPurchasesList.get(1)).thenReturn(listDTO.get(1));
        List<PurchaseDTO> createList = new ArrayList<>() {{
            add(mockPurchasesList.get(0));
            add(mockPurchasesList.get(1));
        }};
        List<PurchaseDTO> purchaseDTOList = service.createAll(listDTO);
        //then
        assertAll(() -> assertEquals(createList.get(0).getId(), purchaseDTOList.get(0).getId()),
                () -> assertEquals(createList.get(0).getCustomerDTO(), purchaseDTOList.get(0).getCustomerDTO()),
                () -> assertEquals(createList.get(0).getPurchaseTime(), purchaseDTOList.get(0).getPurchaseTime()),
                () -> assertEquals(createList.get(1).getId(), purchaseDTOList.get(1).getId()),
                () -> assertEquals(createList.get(1).getCustomerDTO(), purchaseDTOList.get(1).getCustomerDTO()),
                () -> assertEquals(createList.get(1).getPurchaseTime(), purchaseDTOList.get(1).getPurchaseTime()));
    }

    @Test
    void updateTest() {
        //given
        int purchaseID = 1;
        String newName = "Updated Purchase";
        Purchase purchase = Purchase.builder().id(purchaseID).customer(Customer.builder()
                .id(4).name("Alexis Sanchez").email("Sanchez@com").password("alex").build())
                .purchaseTime(Timestamp.valueOf("2007-09-10 00:00:00.0")).build();
        PurchaseDTO expectedPurchaseDTO = PurchaseDTO.builder().id(purchaseID).customerDTO(CustomerDTO.builder()
                .id(4).name("Alexis Sanchez").email("Sanchez@com").password("alex").build())
                .purchaseTime(Timestamp.valueOf("2007-09-10 00:00:00.0")).build();
        //when
        when(repository.getById(purchaseID)).thenReturn(purchase);
        when(modelMapper.map(purchase, PurchaseDTO.class)).thenReturn(expectedPurchaseDTO);
        when(repository.getById(purchaseID)).thenReturn(purchase);
        PurchaseDTO actualUpdatedPurchase = service.update(expectedPurchaseDTO);
        // then
        assertAll(() -> assertEquals(purchaseID, actualUpdatedPurchase.getId()),
                () -> assertEquals(newName, actualUpdatedPurchase.getCustomerDTO().getName())
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
