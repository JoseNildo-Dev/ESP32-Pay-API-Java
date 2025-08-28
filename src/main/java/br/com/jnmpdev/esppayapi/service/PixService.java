package br.com.jnmpdev.esppayapi.service;

import br.com.jnmpdev.esppayapi.dto.DeviceSelfRegisterRequest;
import br.com.jnmpdev.esppayapi.gateway.PaymentGatewayService;
import br.com.jnmpdev.esppayapi.model.PayDevice;
import br.com.jnmpdev.esppayapi.model.PixTransaction;
import br.com.jnmpdev.esppayapi.repository.PayDeviceRepository;
import br.com.jnmpdev.esppayapi.repository.PixTransactionRepository;
import br.com.jnmpdev.esppayapi.dto.PixRequest;
import br.com.jnmpdev.esppayapi.dto.PixResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class PixService {

    @Autowired
    private PixTransactionRepository pixTransactionRepository;

    @Autowired
    private PayDeviceRepository payDeviceRepository;

    @Autowired
    private Map<String, PaymentGatewayService> gatewayServices;

    public PixResponse generatePix(PixRequest request) {
        PayDevice device = payDeviceRepository.findById(request.getDeviceId())
                .orElseThrow(() -> new RuntimeException("Dispositivo não encontrado"));

        PaymentGatewayService gatewayService = gatewayServices.get(device.getGateway().name());

        if (gatewayService == null) {
            throw new RuntimeException("Gateway não suportado: " + device.getGateway());
        }

        PixResponse response = gatewayService.generateQr(request, device);

        if (device.isLogEnabled()) {
            PixTransaction transaction = new PixTransaction();
            transaction.setAmount(request.getAmount());
            transaction.setDescription(request.getDescription());
            transaction.setQrCode(response.getQrCode());
            transaction.setStatus(response.getStatus());
            transaction.setCreatedAt(LocalDateTime.now());
            transaction.setDevice(device);

            pixTransactionRepository.save(transaction);
        }

        return response;
    }


    // Comentário: consulta o status de uma transação
    /*public PixResponse getStatus(Long transactionId) {
        PixTransaction transaction = pixTransactionRepository.findById(transactionId)
                .orElseThrow(() -> new RuntimeException("Transação não encontrada"));

        PixResponse response = new PixResponse();
        response.setQrCode(transaction.getQrCode());
        response.setStatus(transaction.getStatus());

        return response;
    }*/

    public List<PixTransaction> getTransactionsByDevice(Long deviceId) {
        return pixTransactionRepository.findByDeviceId(deviceId);
    }

    public PixResponse simulateStatus(Long transactionId, String newStatus) {
        PixTransaction transaction = pixTransactionRepository.findById(transactionId)
                .orElseThrow(() -> new RuntimeException("Transação não encontrada"));

        transaction.setStatus(newStatus);
        pixTransactionRepository.save(transaction);

        PixResponse response = new PixResponse();
        response.setQrCode(transaction.getQrCode());
        response.setStatus(transaction.getStatus());

        return response;
    }

    public PixResponse cancelTransaction(Long transactionId, String reason) {
        PixTransaction transaction = pixTransactionRepository.findById(transactionId)
                .orElseThrow(() -> new RuntimeException("Transação não encontrada"));

        transaction.setStatus("CANCELLED");
        transaction.setDescription(transaction.getDescription() + " [Cancelado: " + reason + "]");
        pixTransactionRepository.save(transaction);

        PixResponse response = new PixResponse();
        response.setQrCode(transaction.getQrCode());
        response.setStatus(transaction.getStatus());

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



}

