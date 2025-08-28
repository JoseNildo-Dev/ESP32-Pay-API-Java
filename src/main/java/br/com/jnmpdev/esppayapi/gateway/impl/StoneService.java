package br.com.jnmpdev.esppayapi.gateway.impl;

import br.com.jnmpdev.esppayapi.dto.PixGatewayResponse;
import br.com.jnmpdev.esppayapi.dto.PixRequest;
import br.com.jnmpdev.esppayapi.gateway.PixGateway;
import br.com.jnmpdev.esppayapi.model.PayDevice;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service("STONE")
public class StoneService implements PixGateway {

    @Override
    public PixGatewayResponse generateQr(PixRequest request, PayDevice device) {
        PixGatewayResponse response = new PixGatewayResponse();
        response.setQrCode("STONE-QR-" + System.currentTimeMillis());
        response.setGatewayTransactionId("STONE-TX-" + UUID.randomUUID().toString());
        response.setExpiresAt(LocalDateTime.now().plusMinutes(1));
        return response;
    }

}
