package br.com.jnmpdev.esppayapi.model;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.Data;

@Data
@Entity
public class PixTransaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private BigDecimal amount;
    private String description;
    private String qrCode;
    private String status; // "PENDING", "PAID", "EXPIRED"
    private LocalDateTime createdAt;

    @ManyToOne
    @JoinColumn(name = "device_id")
    private PayDevice device;

    // Comentário: representa uma transação Pix gerada por um dispositivo físico
}
