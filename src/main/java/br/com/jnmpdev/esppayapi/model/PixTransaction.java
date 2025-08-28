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
    @Column(name = "qr_code")
    private String qrCode;
    private String status; // "PENDING", "PAID", "EXPIRED"
    private LocalDateTime createdAt;

    @Column(name = "gateway_transaction_id")
    private String gatewayTransactionId;


    @ManyToOne
    @JoinColumn(name = "device_id")
    private PayDevice device;

    @Column(name = "expires_at")
    private LocalDateTime expiresAt;

    public LocalDateTime getExpiresAt() {
        return expiresAt;
    }

    public void setExpiresAt(LocalDateTime expiresAt) {
        this.expiresAt = expiresAt;
    }

    public String getQrCode() {
        return qrCode;
    }

    public void setQrCode(String qrCode) {
        this.qrCode = qrCode;
    }


    // Comentário: representa uma transação Pix gerada por um dispositivo físico
}
