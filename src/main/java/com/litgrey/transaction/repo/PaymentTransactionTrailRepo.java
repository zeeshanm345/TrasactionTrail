package com.litgrey.transaction.repo;

import com.litgrey.transaction.entity.PaymentTransactionTrail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PaymentTransactionTrailRepo extends JpaRepository<PaymentTransactionTrail, Long> {
    List<PaymentTransactionTrail> findByAccountNumber(String accountNumber);
}
