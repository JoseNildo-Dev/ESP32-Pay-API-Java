# ESP32-Pay-API-Java

API REST em Java para gerenciamento de dispositivos de pagamento via Pix, com suporte a múltiplos gateways e integração com firmware embarcado (ESP32, RPi, etc.).

---

## 🚀 Funcionalidades já implementadas

### 📦 Dispositivos (`PayDevice`)
- [x] Auto-cadastro via firmware (`POST /device/self-register`)
- [x] Consulta por serial (`GET /device/serial/{serial}`)
- [x] Ativação manual via painel (`PUT /device/{id}/activate`)
- [x] CRUD completo de dispositivos
- [x] Enum `PaymentGateway` com suporte a MP, Stone e PagSeguro

### 💸 Pix (`PixTransaction`)
- [x] Geração de QR Pix (`POST /pix/generate`)
- [x] Delegação dinâmica por gateway via `PaymentGatewayService`
- [x] Interface `PixGateway` com contrato padronizado
- [x] Serviços mockados para MP, Stone e PagSeguro
- [x] Registro de transações com log opcional
- [x] Consulta de status (`GET /pix/{id}/status`)
- [x] Listagem de transações por dispositivo (`GET /device/{id}/transactions`)
- [x] Simulação de pagamento (`POST /pix/{id}/simulate-status`)
- [x] Cancelamento com validação (`POST /pix/{id}/cancel`)
- [x] Resumo por dispositivo (`GET /pix/device/{id}/summary`)
- [x] Validação de estado: não permite cancelar transações pagas
- [x] Tratamento global de exceções com resposta 400

---

## 🛠️ Em andamento / TODO

### 🔐 Segurança
- [ ] Autenticação leve por `authToken` no dispositivo
- [ ] Validação de token no endpoint de geração de Pix

### 🧠 Painel Admin (React)
- [ ] Listagem de dispositivos com filtro por status
- [ ] Ativação manual via UI
- [ ] Visualização de transações por dispositivo
- [ ] Simulação de pagamento

### 📡 Comunicação com ESP
- [ ] Consulta de status via serial
- [ ] Geração de Pix com autenticação
- [ ] Recebimento de confirmação de pagamento

---

## 🧪 Testes automatizados

Script `testes-pix.sh` realiza:

- Ping do dispositivo
- Geração de Pix
- Consulta de status
- Simulação de pagamento
- Cancelamento com validação
- Resumo por dispositivo

Saída salva em `saida-testes.txt` com delay entre chamadas.

---

## 🧰 Tecnologias

- Java 21
- Spring Boot 3.5.5
- Spring Data JPA
- H2 Database (modo arquivo)
- Maven
- RESTful API

---

## 📂 Estrutura de pacotes

```plaintext
br.com.jnmpdev.esppayapi
├── controller
├── service
├── gateway
│   ├── PaymentGatewayService.java
│   ├── PixGateway.java
│   └── impl/
├── model
├── dto
├── repository
├── exception
