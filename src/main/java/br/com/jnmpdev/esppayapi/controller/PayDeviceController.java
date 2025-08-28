package br.com.jnmpdev.esppayapi.controller;

import br.com.jnmpdev.esppayapi.dto.DeviceSelfRegisterRequest;
import br.com.jnmpdev.esppayapi.model.PayDevice;
import br.com.jnmpdev.esppayapi.model.PaymentGateway;
import br.com.jnmpdev.esppayapi.service.PayDeviceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/device")
public class PayDeviceController {

    @Autowired
    private PayDeviceService payDeviceService;

    // Comentário: cadastra ou atualiza um dispositivo
    @PostMapping
    public ResponseEntity<PayDevice> saveDevice(@RequestBody PayDevice device) {
        PayDevice saved = payDeviceService.save(device);
        return ResponseEntity.ok(saved);
    }

    // Comentário: busca um dispositivo por ID
    @GetMapping("/{id}")
    public ResponseEntity<PayDevice> getDevice(@PathVariable Long id) {
        return payDeviceService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Comentário: lista todos os dispositivos cadastrados
    @GetMapping
    public ResponseEntity<List<PayDevice>> listDevices() {
        return ResponseEntity.ok(payDeviceService.findAll());
    }

    @GetMapping("/gateways")
    public ResponseEntity<List<String>> listGateways() {
        List<String> gateways = Arrays.stream(PaymentGateway.values())
                .map(Enum::name)
                .toList();
        return ResponseEntity.ok(gateways);
    }

    @GetMapping("/serial/{serial}")
    public ResponseEntity<PayDevice> getBySerial(@PathVariable String serial) {
        return payDeviceService.findBySerial(serial)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/self-register")
    public ResponseEntity<PayDevice> selfRegister(@RequestBody DeviceSelfRegisterRequest request) {
        PayDevice device = payDeviceService.selfRegister(request);
        return ResponseEntity.ok(device);
    }

    @PutMapping("/{id}/activate")
    public ResponseEntity<PayDevice> activateDevice(@PathVariable Long id) {
        PayDevice device = payDeviceService.activateDevice(id);
        return ResponseEntity.ok(device);
    }

    @GetMapping("/{serial}/ping")
    public ResponseEntity<?> pingDevice(@PathVariable String serial) {
        Optional<PayDevice> device = payDeviceService.findBySerial(serial);
        if (device.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Dispositivo não encontrado");
        }

        Map<String, Object> response = new HashMap<>();
        response.put("deviceId", device.get().getId());
        response.put("active", device.get().isActive());
        response.put("logEnabled", device.get().isLogEnabled());
        response.put("gateway", device.get().getGateway());
        response.put("location", device.get().getLocation());

        return ResponseEntity.ok(response);
    }



}
