package br.com.jnmpdev.esppayapi.repository;

import br.com.jnmpdev.esppayapi.model.PixTransaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PixTransactionRepository extends JpaRepository<PixTransaction, Long> {
    // Comentário: permite salvar e consultar transações Pix
}
