package com.litgrey.transaction.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BulkPostResponse {
    private String porOrgacode;
    private String accountNumber;
    private int postedCount;
    private Long requestId;
    private List<Long> createdTranIds;
    private String message;
}
