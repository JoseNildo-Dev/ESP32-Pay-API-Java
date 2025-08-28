package br.com.jnmpdev.esppayapi.repository;

import br.com.jnmpdev.esppayapi.model.PixTransaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PixTransactionRepository extends JpaRepository<PixTransaction, Long> {
    List<PixTransaction> findByDeviceId(Long deviceId);
    // Comentário: permite salvar e consultar transações Pix
    List<PixTransaction> findByStatus(String status);


    List<PixTransaction> findByDeviceIdOrderByCreatedAtDesc(Long deviceId);

}
