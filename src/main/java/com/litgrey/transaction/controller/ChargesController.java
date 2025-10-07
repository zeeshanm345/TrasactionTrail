package com.litgrey.transaction.controller;

import com.litgrey.transaction.entity.Charges;
import com.litgrey.transaction.repo.ChargesRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/charges")
@RequiredArgsConstructor
public class ChargesController {
    private final ChargesRepo repo;

    @GetMapping
    public List<Charges> findAll() { return repo.findAll(); }

    @GetMapping("/by-por")
    public List<Charges> byPor(@RequestParam String por) { return repo.findByPorOrgacode(por); }

    @PostMapping
    public Charges create(@RequestBody Charges c) { return repo.save(c); }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) { repo.deleteById(id); }
}
