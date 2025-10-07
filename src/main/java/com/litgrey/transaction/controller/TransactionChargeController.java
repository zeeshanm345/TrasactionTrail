package com.litgrey.transaction.controller;

import com.litgrey.transaction.entity.TransactionCharge;
import com.litgrey.transaction.repo.TransactionChargeRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/transaction-charges")
@RequiredArgsConstructor
public class TransactionChargeController {
    private final TransactionChargeRepo repo;

    @GetMapping
    public List<TransactionCharge> findAll() { return repo.findAll(); }

    @GetMapping("/by")
    public List<TransactionCharge> findByPorAndTran(@RequestParam String por, @RequestParam String trancode) {
        return repo.findByPorOrgacodeAndTrancode(por, trancode);
    }

    @PostMapping
    public TransactionCharge create(@RequestBody TransactionCharge t) { return repo.save(t); }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) { repo.deleteById(id); }
}
