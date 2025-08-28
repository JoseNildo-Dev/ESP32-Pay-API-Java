#!/bin/bash

LOG="saida-expiracao.txt"
> "$LOG"

echo "📦 Cadastrando dispositivo TEST001..." | tee -a "$LOG"
curl -s -X POST http://localhost:8080/device/self-register \
  -H "Content-Type: application/json" \
  -d '{"serial": "TEST001", "location": "Loja A", "gateway": "MP"}' | tee -a "$LOG"
sleep 1

echo -e "\n🔓 Ativando dispositivo..." | tee -a "$LOG"
curl -s -X PUT http://localhost:8080/device/1/activate | tee -a "$LOG"
sleep 1

echo -e "\n💸 Gerando Pix..." | tee -a "$LOG"
generate=$(curl -s -X POST http://localhost:8080/pix/generate \
  -H "Content-Type: application/json" \
  -d '{"amount": 25.00, "description": "Teste expiração", "deviceId": 1}')
echo "$generate" | tee -a "$LOG"
transactionId=$(echo "$generate" | jq -r '.transactionId')
sleep 1

echo -e "\n🔍 Consultando status inicial..." | tee -a "$LOG"
curl -s http://localhost:8080/pix/$transactionId/status | tee -a "$LOG"
sleep 1

echo -e "\n⏳ Aguardando expiração..." | tee -a "$LOG"
sleep 65

echo -e "\n🔍 Consultando status após expiração..." | tee -a "$LOG"
curl -s http://localhost:8080/pix/$transactionId/status | tee -a "$LOG"
sleep 1

echo -e "\n✅ Tentando simular pagamento após expiração..." | tee -a "$LOG"
curl -s -X POST http://localhost:8080/pix/$transactionId/simulate-status \
  -H "Content-Type: application/json" \
  -d '{"status": "PAID"}' | tee -a "$LOG"
sleep 1

echo -e "\n❌ Tentando cancelar transação expirada..." | tee -a "$LOG"
curl -s -X POST http://localhost:8080/pix/$transactionId/cancel \
  -H "Content-Type: application/json" \
  -d '{"reason": "timeout"}' | tee -a "$LOG"
sleep 1

echo -e "\n📊 Resumo do dispositivo..." | tee -a "$LOG"
curl -s http://localhost:8080/pix/device/1/summary | tee -a "$LOG"
