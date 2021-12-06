package com.flashsuppressor.java.lab.service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PurchaseDTO {
    private int id;
    private int customerId;
    private Timestamp purchaseTime;

}
