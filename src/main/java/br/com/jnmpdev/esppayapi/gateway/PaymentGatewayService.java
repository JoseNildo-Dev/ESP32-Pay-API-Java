package br.com.jnmpdev.esppayapi.gateway;

import br.com.jnmpdev.esppayapi.dto.PixRequest;
import br.com.jnmpdev.esppayapi.dto.PixResponse;
import br.com.jnmpdev.esppayapi.model.PayDevice;

public interface PaymentGatewayService {
    PixResponse generateQr(PixRequest request, PayDevice device);
}
