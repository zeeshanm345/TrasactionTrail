package com.litgrey.transaction;

import com.litgrey.transaction.entity.*;
import com.litgrey.transaction.repo.*;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TransactionDataSeeder implements CommandLineRunner {

    private final TransactionTypeRepo transactionTypeRepo;
    private final TransactionChargeRepo transactionChargeRepo;
    private final ChargesRepo chargesRepo;

    @Override
    public void run(String... args) {
        System.out.println("🚀 Seeding reference data for TransactionType, TransactionCharge, and Charges...");

        // --- Seed lg_tr_transactiontype ---
        transactionTypeRepo.save(new TransactionType(
                new TransactionType.TransactionTypeId("001", "101"), "Purchase Order"));
        transactionTypeRepo.save(new TransactionType(
                new TransactionType.TransactionTypeId("001", "102"), "Sales Order"));
        transactionTypeRepo.save(new TransactionType(
                new TransactionType.TransactionTypeId("001", "103"), "Payable Payment"));
        transactionTypeRepo.save(new TransactionType(
                new TransactionType.TransactionTypeId("001", "104"), "Receivable Payment"));

        // --- Seed lg_pr_charges ---
        chargesRepo.save(new Charges(1L, "001", "01", "Sales Order"));
        chargesRepo.save(new Charges(2L,"001", "02", "Purchase Order"));
        chargesRepo.save(new Charges(3L, "001", "03", "Payment"));

        // --- Seed lg_gp_transactioncharge ---
        transactionChargeRepo.save(
                TransactionCharge.builder()
                        .chrgcode("03")
                        .porOrgacode("001")
                        .trancode("103")
                        .glcode("GL103PAY")
                        .chargedesc("Payment Charge (Payable)")
                        .build()
        );

        transactionChargeRepo.save(
                TransactionCharge.builder()
                        .chrgcode("03")
                        .porOrgacode("001")
                        .trancode("104")
                        .glcode("GL104REC")
                        .chargedesc("Payment Charge (Receivable)")
                        .build()
        );

        System.out.println("✅ Reference data seeded successfully:");
        System.out.println(" - Transaction Types: 101, 102, 103, 104");
        System.out.println(" - Charges: 01, 02, 03");
        System.out.println(" - TransactionCharge links: (03,103), (03,104)");
    }
}
