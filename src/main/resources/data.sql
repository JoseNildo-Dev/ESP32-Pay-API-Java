-- Dispositivo ESP32 com log ativado
INSERT INTO pay_device (name, location, gateway, serial, type, active, log_enabled)
VALUES ('ESP32-C3 #01', 'Rua A', 'MP', 'ABC123', 'ESP32-C3', true, true);

-- Dispositivo Raspberry com log desativado
INSERT INTO pay_device (name, location, gateway, serial, type, active, log_enabled)
VALUES ('RPI Nano #02', 'Rua B', 'STONE', 'XYZ789', 'RPI Nano', true, false);
