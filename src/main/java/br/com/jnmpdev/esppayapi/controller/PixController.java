package br.com.jnmpdev.esppayapi.controller;

import br.com.jnmpdev.esppayapi.dto.PixRequest;
import br.com.jnmpdev.esppayapi.dto.PixResponse;
import br.com.jnmpdev.esppayapi.service.PixService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/pix")
public class PixController {

    @Autowired
    private PixService pixService;

    // Comentário: gera um QR Pix mockado com base no dispositivo e valor
    @PostMapping("/generate")
    public ResponseEntity<PixResponse> generatePix(@RequestBody PixRequest request) {
        PixResponse response = pixService.generatePix(request);
        return ResponseEntity.ok(response);
    }

    // Comentário: consulta o status de uma transação Pix
    @GetMapping("/status/{id}")
    public ResponseEntity<PixResponse> getStatus(@PathVariable Long id) {
        PixResponse response = pixService.getStatus(id);
        return ResponseEntity.ok(response);
    }
}
