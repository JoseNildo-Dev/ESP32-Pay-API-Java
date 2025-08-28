package br.com.jnmpdev.esppayapi.service;

import br.com.jnmpdev.esppayapi.model.PixTransaction;
import br.com.jnmpdev.esppayapi.repository.PixTransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class PixScheduler {

    @Autowired
    private PixTransactionRepository pixTransactionRepository;

    @Scheduled(fixedRate = 60000) // a cada 60 segundos
    public void expirePendingTransactions() {
        LocalDateTime now = LocalDateTime.now();
        List<PixTransaction> pending = pixTransactionRepository.findByStatus("PENDING");

        for (PixTransaction tx : pending) {
            if (tx.getExpiresAt() != null && tx.getExpiresAt().isBefore(now)) {
                tx.setStatus("EXPIRED");
                pixTransactionRepository.save(tx);
            }
        }
    }
}

