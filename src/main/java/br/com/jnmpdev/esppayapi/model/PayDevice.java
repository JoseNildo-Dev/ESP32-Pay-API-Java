package br.com.jnmpdev.esppayapi.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class PayDevice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String location;
    @Enumerated(EnumType.STRING)
    private PaymentGateway gateway;

    @Column(unique = true, nullable = false)
    private String serial;
    private String type;
    private boolean active;

    private boolean logEnabled; // Comentário: define se o dispositivo deve registrar transações

    // Comentário: representa qualquer dispositivo físico que gera cobranças Pix
}
