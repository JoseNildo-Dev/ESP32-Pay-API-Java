package br.com.jnmpdev.esppayapi.gateway.impl;

import br.com.jnmpdev.esppayapi.dto.PixRequest;
import br.com.jnmpdev.esppayapi.dto.PixResponse;
import br.com.jnmpdev.esppayapi.model.PayDevice;
import br.com.jnmpdev.esppayapi.gateway.PaymentGatewayService;
import org.springframework.stereotype.Service;

@Service("MP")
public class MercadoPagoService implements PaymentGatewayService {

    @Override
    public PixResponse generateQr(PixRequest request, PayDevice device) {
        PixResponse response = new PixResponse();
        response.setQrCode("MP-QR-" + System.currentTimeMillis());
        response.setStatus("PENDING");
        return response;
    }
}
