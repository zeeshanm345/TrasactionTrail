package com.litgrey.transaction.repo;

import com.litgrey.transaction.entity.TransactionType;
import com.litgrey.transaction.entity.TransactionType.TransactionTypeId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionTypeRepo extends JpaRepository<TransactionType, TransactionTypeId> {
}
