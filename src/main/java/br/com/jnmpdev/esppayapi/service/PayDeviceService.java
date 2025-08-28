package br.com.jnmpdev.esppayapi.service;

import br.com.jnmpdev.esppayapi.dto.DeviceSelfRegisterRequest;
import br.com.jnmpdev.esppayapi.model.PayDevice;
import br.com.jnmpdev.esppayapi.repository.PayDeviceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.List;

@Service
public class PayDeviceService {

    @Autowired
    private PayDeviceRepository payDeviceRepository;


    // Comentário: busca um dispositivo pelo ID
    public Optional<PayDevice> findById(Long id) {
        return payDeviceRepository.findById(id);
    }

    // Comentário: salva ou atualiza um dispositivo
    public PayDevice save(PayDevice device) {
        return payDeviceRepository.save(device);
    }

    // Comentário: lista todos os dispositivos
    public List<PayDevice> findAll() {
        return payDeviceRepository.findAll();
    }

    public PayDevice selfRegister(DeviceSelfRegisterRequest request) {
        Optional<PayDevice> existing = payDeviceRepository.findBySerial(request.getSerial());

        if (existing.isPresent()) {
            return existing.get(); // Comentário: já existe, retorna sem duplicar
        }

        PayDevice device = new PayDevice();
        device.setName(request.getName());
        device.setSerial(request.getSerial());
        device.setType(request.getType());
        device.setLocation(request.getLocation());
        device.setGateway(request.getGateway());
        device.setActive(false);      // Comentário: aguarda ativação manual
        device.setLogEnabled(false);  // Comentário: log desativado por padrão

        return payDeviceRepository.save(device);
    }

    public Optional<PayDevice> findBySerial(String serial) {
        return payDeviceRepository.findBySerial(serial);
    }

    public PayDevice activateDevice(Long id) {
        PayDevice device = payDeviceRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Dispositivo não encontrado"));

        device.setActive(true);
        return payDeviceRepository.save(device);
    }


}
