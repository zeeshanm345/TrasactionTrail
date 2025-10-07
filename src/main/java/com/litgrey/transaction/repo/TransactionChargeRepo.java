package com.litgrey.transaction.repo;

import com.litgrey.transaction.entity.TransactionCharge;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionChargeRepo extends JpaRepository<TransactionCharge, Long> {
    List<TransactionCharge> findByPorOrgacodeAndTrancode(String porOrgacode, String trancode);
}
