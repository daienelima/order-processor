from kafka import KafkaProducer
import json
from datetime import datetime
import hashlib

producer = KafkaProducer(
    bootstrap_servers='localhost:9092',
    value_serializer=lambda v: json.dumps(v, default=str).encode('utf-8'),
    key_serializer=str.encode
)

produtos = [
    {"nome": "Teclado", "precoUnitario": 150.00, "quantidade": 1},
    {"nome": "Mouse", "precoUnitario": 90.50, "quantidade": 2}
]

def calcular_valor_total(produtos):
    return sum(p["precoUnitario"] * p["quantidade"] for p in produtos)

def gerar_codigo_idempotente(data_criacao: datetime, valor_total: float):
    raw = f"{data_criacao.isoformat()}-{valor_total:.2f}"
    codigo = hashlib.sha256(raw.encode()).hexdigest()[:20]
    return f"IDEMP-{codigo}"

data_criacao = datetime.now()
valor_total = calcular_valor_total(produtos)
codigo_externo = gerar_codigo_idempotente(data_criacao, valor_total)

mensagem = {
    "codigoExterno": codigo_externo,
    "dataCriacao": data_criacao.isoformat(),
    "produtos": produtos
}

# Envia a mensagem
producer.send('tp-pedidos-recebidos', key="pedido-001", value=mensagem)
producer.flush()

print(f"✅ Mensagem enviada com sucesso!")
print(f"🆔 codigoExterno: {codigo_externo}")
print(f"💰 valorTotal: R$ {valor_total:.2f}")
