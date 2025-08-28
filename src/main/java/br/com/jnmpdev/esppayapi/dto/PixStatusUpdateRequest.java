package br.com.jnmpdev.esppayapi.dto;

import lombok.Data;

@Data
public class PixStatusUpdateRequest {
    private String status; // Ex: PAID, FAILED, EXPIRED
}
