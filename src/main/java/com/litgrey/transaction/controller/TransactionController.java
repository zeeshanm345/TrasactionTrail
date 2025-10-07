package com.litgrey.transaction.controller;

import com.litgrey.transaction.dto.BulkPostRequest;
import com.litgrey.transaction.dto.BulkPostResponse;
import com.litgrey.transaction.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/transactions")
@RequiredArgsConstructor
public class TransactionController {

    private final TransactionService service;

    @PostMapping("/bulk")
    public ResponseEntity<BulkPostResponse> postBulk(@RequestBody BulkPostRequest req) {
        return ResponseEntity.ok(service.postBulk(req));
    }

    @PostMapping("/{id}/cancel")
    public ResponseEntity<String> cancel(@PathVariable Long id, @RequestParam String user) {
        service.cancelTransaction(id, user);
        return ResponseEntity.ok("Canceled");
    }

    @PostMapping("/{id}/reverse")
    public ResponseEntity<String> reverse(@PathVariable Long id, @RequestParam String user) {
        Long revId = service.reverseTransaction(id, user);
        return ResponseEntity.ok("Reversal created: " + revId);
    }
}
