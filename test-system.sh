#!/bin/bash

echo "🔁 Testando /device/TEST001/ping"
curl -s http://localhost:8080/device/TEST001/ping | jq

echo "💸 Gerando Pix"
generate=$(curl -s -X POST http://localhost:8080/pix/generate \
  -H "Content-Type: application/json" \
  -d '{"amount": 25.00, "description": "Teste via curl", "deviceId": 1}')

echo "$generate" | jq

transactionId=$(echo "$generate" | jq -r '.transactionId')

echo "🔍 Consultando status da transação $transactionId"
curl -s http://localhost:8080/pix/$transactionId/status | jq

echo "✅ Simulando pagamento"
curl -s -X POST http://localhost:8080/pix/$transactionId/simulate-status \
  -H "Content-Type: application/json" \
  -d '{"status": "PAID"}' | jq

echo "🔍 Consultando status após pagamento"
curl -s http://localhost:8080/pix/$transactionId/status | jq

echo "❌ Simulando cancelamento"
curl -s -X POST http://localhost:8080/pix/$transactionId/cancel \
  -H "Content-Type: application/json" \
  -d '{"reason": "timeout"}' | jq

echo "📊 Resumo do dispositivo"
curl -s http://localhost:8080/pix/device/1/summary | jq
