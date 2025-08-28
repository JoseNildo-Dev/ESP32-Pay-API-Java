#!/bin/bash

LOG="saida-testes.txt"
> "$LOG" # limpa o arquivo antes de começar

echo "🔁 Teste 1: /device/TEST001/ping" | tee -a "$LOG"
curl -s http://localhost:8080/device/TEST001/ping | tee -a "$LOG"
sleep 1

echo -e "\n💸 Teste 2: Gerar Pix" | tee -a "$LOG"
generate=$(curl -s -X POST http://localhost:8080/pix/generate \
  -H "Content-Type: application/json" \
  -d '{"amount": 25.00, "description": "Teste via script", "deviceId": 1}')
echo "$generate" | tee -a "$LOG"
transactionId=$(echo "$generate" | jq -r '.transactionId')
sleep 1

echo -e "\n🔍 Teste 3: Consultar status da transação $transactionId" | tee -a "$LOG"
curl -s http://localhost:8080/pix/$transactionId/status | tee -a "$LOG"
sleep 1

echo -e "\n✅ Teste 4: Simular pagamento" | tee -a "$LOG"
curl -s -X POST http://localhost:8080/pix/$transactionId/simulate-status \
  -H "Content-Type: application/json" \
  -d '{"status": "PAID"}' | tee -a "$LOG"
sleep 1

echo -e "\n🔍 Teste 5: Consultar status após pagamento" | tee -a "$LOG"
curl -s http://localhost:8080/pix/$transactionId/status | tee -a "$LOG"
sleep 1

echo -e "\n❌ Teste 6: Cancelar transação" | tee -a "$LOG"
curl -s -X POST http://localhost:8080/pix/$transactionId/cancel \
  -H "Content-Type: application/json" \
  -d '{"reason": "timeout"}' | tee -a "$LOG"
sleep 1

echo -e "\n📊 Teste 7: Resumo do dispositivo" | tee -a "$LOG"
curl -s http://localhost:8080/pix/device/1/summary | tee -a "$LOG"
