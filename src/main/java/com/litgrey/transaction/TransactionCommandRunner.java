package com.litgrey.transaction;

import com.litgrey.transaction.dto.*;
import com.litgrey.transaction.entity.*;
import com.litgrey.transaction.repo.*;
import com.litgrey.transaction.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class TransactionCommandRunner implements CommandLineRunner {

    private final TransactionService transactionService;
    private final TransactionTypeRepo transactionTypeRepo;
    private final TransactionChargeRepo transactionChargeRepo;

    @Override
    public void run(String... args) {

        System.out.println("🚀 Initializing sample transaction posting scenarios...");

        // ---- Seed Reference Data ----
        TransactionType.TransactionTypeId typeId1 = new TransactionType.TransactionTypeId("001", "103"); // Payable Payment
        TransactionType.TransactionTypeId typeId2 = new TransactionType.TransactionTypeId("001", "104"); // Receivable Payment
        transactionTypeRepo.save(new TransactionType(typeId1, "Payable Payment"));
        transactionTypeRepo.save(new TransactionType(typeId2, "Receivable Payment"));

        // Charges
        transactionChargeRepo.save(new TransactionCharge(
                new TransactionChargeId("03", "001", "103"),
                "GL1001",
                "Payment Charge (Payable)"
        ));
        transactionChargeRepo.save(new TransactionCharge(
                new TransactionChargeId("03", "001", "104"),
                "GL1002",
                "Payment Charge (Receivable)"
        ));

        // ---- Scenario 1: Successful Transaction with Multiple Charges ----
        BulkPostRequest successReq = new BulkPostRequest();
        successReq.setRequestId(UUID.randomUUID());
        successReq.setPorOrgacode("001");
        successReq.setAccountNumber("ACC001");
        successReq.setTransType("103");
        successReq.setAmount(new BigDecimal("10000"));
        successReq.setCreatedBy("system");
        successReq.setCharges(List.of(
                new TransactionChargeRequest("03", new BigDecimal("7000")),
                new TransactionChargeRequest("03", new BigDecimal("3000"))
        ));

        // ---- Scenario 2: Invalid Amount ----
        BulkPostRequest invalidAmount = new BulkPostRequest();
        invalidAmount.setRequestId(UUID.randomUUID().toString());
        invalidAmount.setPorOrgacode("001");
        invalidAmount.setAccountNumber("ACC002");
        invalidAmount.setTransType("103");
        invalidAmount.setAmount(BigDecimal.ZERO);
        invalidAmount.setCreatedBy("tester");
        invalidAmount.setCharges(List.of(
                new TransactionChargeRequest("03", new BigDecimal("500"))
        ));

        // ---- Scenario 3: Invalid Transaction Type ----
        BulkPostRequest invalidTranType = new BulkPostRequest();
        invalidTranType.setRequestId(UUID.randomUUID().toString());
        invalidTranType.setPorOrgacode("001");
        invalidTranType.setAccountNumber("ACC003");
        invalidTranType.setTransType("999"); // invalid type
        invalidTranType.setAmount(new BigDecimal("2000"));
        invalidTranType.setCreatedBy("tester");
        invalidTranType.setCharges(List.of(
                new TransactionChargeRequest("03", new BigDecimal("2000"))
        ));

        // ---- Scenario 4: Invalid Charge Mapping ----
        BulkPostRequest invalidCharge = new BulkPostRequest();
        invalidCharge.setRequestId(UUID.randomUUID().toString());
        invalidCharge.setPorOrgacode("001");
        invalidCharge.setAccountNumber("ACC004");
        invalidCharge.setTransType("104");
        invalidCharge.setAmount(new BigDecimal("5000"));
        invalidCharge.setCreatedBy("tester");
        invalidCharge.setCharges(List.of(
                new TransactionChargeRequest("99", new BigDecimal("5000")) // invalid charge code
        ));

        // ---- Bulk Posting ----
        List<BulkPostRequest> requests = List.of(successReq, invalidAmount, invalidTranType, invalidCharge);
        var responses = transactionService.postBulkTransactions(requests);

        System.out.println("🧾 Transaction Processing Summary:");
        responses.forEach(r ->
                System.out.printf("RequestId: %s | Account: %s | Charge: %s | Status: %s | Message: %s%n",
                        r.getRequestId(),
                        r.getAccountNumber(),
                        r.getChargeCode(),
                        r.isSuccess() ? "✅ SUCCESS" : "❌ FAILED",
                        r.getMessage())
        );

        System.out.println("✅ All test scenarios executed.");
    }
}
