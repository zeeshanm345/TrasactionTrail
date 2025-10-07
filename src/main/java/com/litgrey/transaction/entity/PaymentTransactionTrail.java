package com.litgrey.transaction.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "lg_tr_payment_transaction_trail")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PaymentTransactionTrail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long tranId;

    @Column(nullable = false)
    private LocalDate tranDate;

    @Column(nullable = false)
    private LocalDate valueDate;

    @Column(nullable = false, length = 100)
    private String accountNumber;

    private Long transSequenceId = 1L;

    @Column(nullable = false, length = 10)
    private String transType;

    @Column(nullable = false, length = 10)
    private String chrgcode;

    @Column(nullable = false, precision = 18, scale = 2)
    private BigDecimal amount;

    @Column(nullable = false, length = 100)
    private String createdBy;

    @Column(length = 100)
    private String approvedBy;

    @Column(nullable = false, length = 100)
    private String authorizedBy;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createDateTime = LocalDateTime.now();

    @Column(nullable = false)
    private boolean isCanceled = false;

    @Column(nullable = false)
    private boolean isReversal = false;

    @Column(nullable = false)
    private boolean tranStatus = false;
}
