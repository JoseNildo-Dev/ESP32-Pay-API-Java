package br.com.jnmpdev.esppayapi.gateway;

import br.com.jnmpdev.esppayapi.dto.PixGatewayResponse;
import br.com.jnmpdev.esppayapi.dto.PixRequest;
import br.com.jnmpdev.esppayapi.model.PayDevice;

public interface PixGateway {
    PixGatewayResponse generateQr(PixRequest request, PayDevice device);
}
