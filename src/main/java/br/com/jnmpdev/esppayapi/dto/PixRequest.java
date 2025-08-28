package br.com.jnmpdev.esppayapi.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class PixRequest {
    private BigDecimal amount;
    private String description;
    private Long deviceId;

    // Comentário: representa os dados enviados pelo dispositivo para gerar um QR Pix
}
