package br.com.jnmpdev.esppayapi.repository;

import br.com.jnmpdev.esppayapi.model.PayDevice;
import br.com.jnmpdev.esppayapi.model.PixTransaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PayDeviceRepository extends JpaRepository<PayDevice, Long> {
    // Comentário: permite buscar dispositivos por ID, listar todos, etc.
    PayDevice findBySerial(String serial);

}
