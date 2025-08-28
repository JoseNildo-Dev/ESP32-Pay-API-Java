package br.com.jnmpdev.esppayapi.dto;

import lombok.Data;

@Data
public class PixCancelRequest {
    private String reason; // Ex: "timeout", "user_cancel", etc.
}
