package com.litgrey.transaction.service;

import com.litgrey.transaction.dto.BulkPostRequest;
import com.litgrey.transaction.dto.BulkPostResponse;
import com.litgrey.transaction.dto.TransactionChargeRequest;
import com.litgrey.transaction.entity.PaymentTransactionTrail;
import com.litgrey.transaction.entity.TransactionCharge;
import com.litgrey.transaction.repo.PaymentTransactionTrailRepo;
import com.litgrey.transaction.repo.TransactionChargeRepo;
import jakarta.persistence.EntityManager;
import jakarta.persistence.LockModeType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TransactionService {

    private final PaymentTransactionTrailRepo trailRepo;
    private final TransactionChargeRepo transactionChargeRepo;
    private final EntityManager entityManager;

    /**
     * Post bulk transaction: for each charge in request create a PaymentTransactionTrail row.
     * This method is transactional and uses DB guarantees. To help concurrency we lock account
     * row-range using a pessimistic write lock on a synthetic entity (using select ... for update)
     * via the EntityManager; H2/MySQL will honor locking when real rows exist. For demo we keep
     * the approach simple and rely on transaction isolation.
     */
    @Transactional
    public BulkPostResponse postBulk(BulkPostRequest req) {
        List<Long> created = new ArrayList<>();

        // Basic validation
        if (req.getCharges()==null || req.getCharges().isEmpty()) {
            return BulkPostResponse.builder()
                    .porOrgacode(req.getPorOrgacode())
                    .accountNumber(req.getAccountNumber())
                    .postedCount(0)
                    .createdTranIds(created)
                    .message("No charges provided")
                    .build();
        }

        // Optionally, verify transaction types / charges exist
        // For each charge create a PaymentTransactionTrail
        LocalDate now = LocalDate.now();
        for (TransactionChargeRequest ch: req.getCharges()) {
            PaymentTransactionTrail trail = PaymentTransactionTrail.builder()
                    .tranDate(now)
                    .valueDate(now)
                    .accountNumber(req.getAccountNumber())
                    .transType(req.getTransType())
                    .chrgcode(ch.getChrgcode())
                    .amount(ch.getAmount())
                    .createdBy(req.getCreatedBy())
                    .authorizedBy(req.getCreatedBy())
                    .tranStatus(true)
                    .isCanceled(false)
                    .isReversal(false)
                    .build();
            PaymentTransactionTrail saved = trailRepo.save(trail);
            created.add(saved.getTranId());
        }

        return BulkPostResponse.builder()
                .porOrgacode(req.getPorOrgacode())
                .accountNumber(req.getAccountNumber())
                .postedCount(created.size())
                .createdTranIds(created)
                .message("Posted")
                .build();
    }

    /**
     * Overloaded method to handle multiple BulkPostRequests at once.
     */
    @Transactional
    public List<BulkPostResponse> postBulk(List<BulkPostRequest> requests) {
        List<BulkPostResponse> responses = new ArrayList<>();
        for (BulkPostRequest req : requests) {
            responses.add(postBulk(req));
        }
        return responses;
    }

    @Transactional
    public void cancelTransaction(Long tranId, String user) {
        PaymentTransactionTrail t = trailRepo.findById(tranId).orElseThrow(() -> new IllegalArgumentException("Not found"));
        if (t.isCanceled()) return;
        t.setCanceled(true);
        t.setApprovedBy(user);
        trailRepo.save(t);
    }

    @Transactional
    public Long reverseTransaction(Long tranId, String user) {
        PaymentTransactionTrail original = trailRepo.findById(tranId).orElseThrow(() -> new IllegalArgumentException("Not found"));
        if (original.isReversal()) throw new IllegalArgumentException("Cannot reverse reversal");
        // create reversal entry with negative amount and link via description using authorizedBy (simple approach)
        PaymentTransactionTrail rev = PaymentTransactionTrail.builder()
                .tranDate(original.getTranDate())
                .valueDate(original.getValueDate())
                .accountNumber(original.getAccountNumber())
                .transType(original.getTransType())
                .chrgcode(original.getChrgcode())
                .amount(original.getAmount().negate())
                .createdBy(user)
                .authorizedBy(user)
                .tranStatus(true)
                .isCanceled(false)
                .isReversal(true)
                .build();
        PaymentTransactionTrail saved = trailRepo.save(rev);
        // mark original as reversed
        original.setReversal(true);
        trailRepo.save(original);
        return saved.getTranId();
    }
}
