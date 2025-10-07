package com.litgrey.transaction.dto;

import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BulkPostRequest {
    private String porOrgacode;
    private String accountNumber;
    private String transType; // 103 or 104
    private String createdBy;
    private Long requestId;
    private List<TransactionChargeRequest> charges;
}
