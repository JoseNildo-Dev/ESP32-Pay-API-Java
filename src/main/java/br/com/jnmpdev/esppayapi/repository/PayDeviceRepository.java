package br.com.jnmpdev.esppayapi.repository;

import br.com.jnmpdev.esppayapi.model.PayDevice;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PayDeviceRepository extends JpaRepository<PayDevice, Long> {
    // Comentário: permite buscar dispositivos por ID, listar todos, etc.
    Optional<PayDevice> findBySerial(String serial);
}
