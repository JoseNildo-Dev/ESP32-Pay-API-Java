package br.com.jnmpdev.esppayapi.service;

import br.com.jnmpdev.esppayapi.dto.PixGatewayResponse;
import br.com.jnmpdev.esppayapi.dto.PixRequest;
import br.com.jnmpdev.esppayapi.dto.PixResponse;
import br.com.jnmpdev.esppayapi.model.PayDevice;
import br.com.jnmpdev.esppayapi.model.PixTransaction;
import br.com.jnmpdev.esppayapi.repository.PayDeviceRepository;
import br.com.jnmpdev.esppayapi.repository.PixTransactionRepository;
import br.com.jnmpdev.esppayapi.gateway.PaymentGatewayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;

@Service
public class PixService {

    @Autowired
    private PixTransactionRepository pixTransactionRepository;

    @Autowired
    private PayDeviceRepository payDeviceRepository;

    @Autowired
    private PaymentGatewayService paymentGatewayService;

    public PixResponse generatePix(PixRequest request) {
        PayDevice device = payDeviceRepository.findById(request.getDeviceId())
                .orElseThrow(() -> new RuntimeException("Dispositivo não encontrado"));

        PixGatewayResponse gatewayResponse = paymentGatewayService.generateQr(request, device);

        PixTransaction transaction = new PixTransaction();
        transaction.setAmount(request.getAmount());
        transaction.setDescription(request.getDescription());
        transaction.setQrCode(gatewayResponse.getQrCode());
        transaction.setStatus("PENDING");
        transaction.setCreatedAt(LocalDateTime.now());
        transaction.setExpiresAt(gatewayResponse.getExpiresAt());
        transaction.setGatewayTransactionId(gatewayResponse.getGatewayTransactionId());
        transaction.setDevice(device);

        pixTransactionRepository.save(transaction);

        PixResponse response = new PixResponse();
        response.setQrCode(transaction.getQrCode());
        response.setStatus(transaction.getStatus());
        response.setTransactionId(transaction.getId());

        return response;
    }

    public PixResponse getStatus(Long transactionId) {
        PixTransaction transaction = pixTransactionRepository.findById(transactionId)
                .orElseThrow(() -> new RuntimeException("Transação não encontrada"));

        PixResponse response = new PixResponse();
        response.setTransactionId(transaction.getId());
        response.setQrCode(transaction.getQrCode());
        response.setStatus(transaction.getStatus());

        return response;
    }

    public PixResponse simulateStatus(Long transactionId, String newStatus) {
        PixTransaction transaction = pixTransactionRepository.findById(transactionId)
                .orElseThrow(() -> new RuntimeException("Transação não encontrada"));

        transaction.setStatus(newStatus);
        pixTransactionRepository.save(transaction);

        PixResponse response = new PixResponse();
        response.setQrCode(transaction.getQrCode());
        response.setStatus(transaction.getStatus());
        response.setTransactionId(transaction.getId());


        return response;
    }

    public PixResponse cancelTransaction(Long transactionId, String reason) {

        PixTransaction transaction = pixTransactionRepository.findById(transactionId)
                .orElseThrow(() -> new RuntimeException("Transação não encontrada"));

        if (!transaction.getStatus().equals("PENDING")) {
            throw new IllegalStateException("Não é possível cancelar uma transação que já foi paga ou cancelada.");
        }


        transaction.setStatus("CANCELLED");
        transaction.setDescription(transaction.getDescription() + " [Cancelado: " + reason + "]");
        pixTransactionRepository.save(transaction);

        PixResponse response = new PixResponse();
        response.setQrCode(transaction.getQrCode());
        response.setStatus(transaction.getStatus());
        response.setTransactionId(transaction.getId());


        return response;
    }

    public List<PixTransaction> getTransactionsByDevice(Long deviceId) {
        return pixTransactionRepository.findByDeviceId(deviceId);
    }

    public Map<String, Object> getDeviceSummary(Long deviceId) {
        List<PixTransaction> transactions = pixTransactionRepository.findByDeviceId(deviceId);

        BigDecimal totalPaid = BigDecimal.ZERO;
        BigDecimal totalCancelled = BigDecimal.ZERO;
        BigDecimal totalPending = BigDecimal.ZERO;
        LocalDateTime lastTransaction = null;

        for (PixTransaction tx : transactions) {
            switch (tx.getStatus()) {
                case "PAID" -> totalPaid = totalPaid.add(tx.getAmount());
                case "CANCELLED", "EXPIRED" -> totalCancelled = totalCancelled.add(tx.getAmount());
                case "PENDING" -> totalPending = totalPending.add(tx.getAmount());
            }
            if (lastTransaction == null || tx.getCreatedAt().isAfter(lastTransaction)) {
                lastTransaction = tx.getCreatedAt();
            }
        }

        Map<String, Object> summary = new HashMap<>();
        summary.put("totalPaid", totalPaid);
        summary.put("totalCancelled", totalCancelled);
        summary.put("totalPending", totalPending);
        summary.put("lastTransaction", lastTransaction);

        return summary;
    }
}
