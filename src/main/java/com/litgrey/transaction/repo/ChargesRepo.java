package com.litgrey.transaction.repo;

import com.litgrey.transaction.entity.Charges;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChargesRepo extends JpaRepository<Charges, Long> {
    List<Charges> findByPorOrgacode(String porOrgacode);
}
