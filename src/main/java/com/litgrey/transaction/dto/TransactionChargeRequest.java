package com.litgrey.transaction.dto;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TransactionChargeRequest {
    private String chrgcode;
    private java.math.BigDecimal amount;
}
