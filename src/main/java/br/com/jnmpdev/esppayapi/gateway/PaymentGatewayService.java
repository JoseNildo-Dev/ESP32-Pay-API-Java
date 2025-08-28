package br.com.jnmpdev.esppayapi.gateway;

import br.com.jnmpdev.esppayapi.dto.PixGatewayResponse;
import br.com.jnmpdev.esppayapi.dto.PixRequest;
import br.com.jnmpdev.esppayapi.dto.PixResponse;
import br.com.jnmpdev.esppayapi.gateway.impl.MercadoPagoService;
import br.com.jnmpdev.esppayapi.gateway.impl.PagSeguroService;
import br.com.jnmpdev.esppayapi.gateway.impl.StoneService;
import br.com.jnmpdev.esppayapi.model.PayDevice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class PaymentGatewayService {

    private final Map<String, PixGateway> gateways;

    public PaymentGatewayService(Map<String, PixGateway> gateways) {
        this.gateways = gateways;
    }

    public PixGatewayResponse generateQr(PixRequest request, PayDevice device) {
        String gatewayKey = String.valueOf(device.getGateway()).toUpperCase();

        PixGateway gateway = gateways.get(gatewayKey);
        if (gateway == null) {
            throw new IllegalArgumentException("Gateway não suportado: " + gatewayKey);
        }

        return gateway.generateQr(request, device);
    }
}
