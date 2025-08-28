package br.com.jnmpdev.esppayapi.dto;

import br.com.jnmpdev.esppayapi.model.PaymentGateway;
import lombok.Data;

@Data
public class DeviceSelfRegisterRequest {
    private String name;
    private String serial;
    private String type;
    private String location;
    private PaymentGateway gateway;
}
