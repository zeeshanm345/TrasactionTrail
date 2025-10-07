package com.litgrey.transaction.controller;

import com.litgrey.transaction.entity.TransactionType;
import com.litgrey.transaction.entity.TransactionType.TransactionTypeId;
import com.litgrey.transaction.repo.TransactionTypeRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/transaction-types")
@RequiredArgsConstructor
public class TransactionTypeController {
    private final TransactionTypeRepo repo;

    @GetMapping
    public List<TransactionType> findAll() { return repo.findAll(); }

    @PostMapping
    public TransactionType create(@RequestBody TransactionType t) { return repo.save(t); }

    @GetMapping("/{por}/{trancode}")
    public ResponseEntity<TransactionType> get(@PathVariable String por, @PathVariable String trancode) {
        return repo.findById(new TransactionTypeId(por, trancode))
                .map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{por}/{trancode}")
    public void delete(@PathVariable String por, @PathVariable String trancode) {
        repo.deleteById(new TransactionTypeId(por, trancode));
    }
}
