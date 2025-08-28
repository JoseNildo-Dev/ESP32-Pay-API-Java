package br.com.jnmpdev.esppayapi.controller;

import br.com.jnmpdev.esppayapi.dto.PixCancelRequest;
import br.com.jnmpdev.esppayapi.dto.PixRequest;
import br.com.jnmpdev.esppayapi.dto.PixResponse;
import br.com.jnmpdev.esppayapi.dto.PixStatusUpdateRequest;
import br.com.jnmpdev.esppayapi.model.PixTransaction;
import br.com.jnmpdev.esppayapi.service.PixService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/pix")
public class PixController {

    @Autowired
    private PixService pixService;

    // Comentário: gera um QR Pix mockado com base no dispositivo e valor
    @PostMapping("/generate")
    public ResponseEntity<PixResponse> generatePix(@RequestBody PixRequest request) {
        PixResponse response = pixService.generatePix(request);
        return ResponseEntity.ok(response);
    }

    // Comentário: consulta o status de uma transação Pix
    //@GetMapping("/status/{id}")
    //public ResponseEntity<PixResponse> getStatus(@PathVariable Long id) {
    //    PixResponse response = pixService.getStatus(id);
    //    return ResponseEntity.ok(response);
    //}

    @GetMapping("/device/{id}/transactions")
    public ResponseEntity<List<PixTransaction>> getTransactions(@PathVariable Long id) {
        List<PixTransaction> transactions = pixService.getTransactionsByDevice(id);
        return ResponseEntity.ok(transactions);
    }

    @PostMapping("/{id}/simulate-status")
    public ResponseEntity<PixResponse> simulateStatus(
            @PathVariable Long id,
            @RequestBody PixStatusUpdateRequest request) {
        PixResponse response = pixService.simulateStatus(id, request.getStatus());
        return ResponseEntity.ok(response);
    }

    @PostMapping("/{id}/cancel")
    public ResponseEntity<PixResponse> cancelTransaction(
            @PathVariable Long id,
            @RequestBody(required = false) PixCancelRequest request) {
        String reason = request != null ? request.getReason() : "timeout";
        PixResponse response = pixService.cancelTransaction(id, reason);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}/status")
    public ResponseEntity<PixResponse> getStatus(@PathVariable Long id) {
        PixResponse response = pixService.getStatus(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/device/{id}/summary")
    public ResponseEntity<?> getDeviceSummary(@PathVariable Long id) {
        Map<String, Object> summary = pixService.getDeviceSummary(id);
        return ResponseEntity.ok(summary);
    }


}
