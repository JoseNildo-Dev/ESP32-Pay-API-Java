package br.com.jnmpdev.esppayapi.dto;

import lombok.Data;

@Data
public class PixResponse {
    private String qrCode;
    private String status;

    // Comentário: representa a resposta da API com o QR gerado e o status da transação
}
