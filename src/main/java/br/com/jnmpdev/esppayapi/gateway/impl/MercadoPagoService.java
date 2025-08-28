package br.com.jnmpdev.esppayapi.gateway.impl;

import br.com.jnmpdev.esppayapi.dto.PixRequest;
import br.com.jnmpdev.esppayapi.dto.PixGatewayResponse;
import br.com.jnmpdev.esppayapi.gateway.PixGateway;
import br.com.jnmpdev.esppayapi.model.PayDevice;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service("MP")
public class MercadoPagoService implements PixGateway {

    @Override
    public PixGatewayResponse generateQr(PixRequest request, PayDevice device) {
        PixGatewayResponse response = new PixGatewayResponse();
        response.setQrCode("MP-QR-" + System.currentTimeMillis());
        response.setGatewayTransactionId("MP-TX-" + UUID.randomUUID().toString());
        response.setExpiresAt(LocalDateTime.now().plusMinutes(1));
        return response;
    }
}


