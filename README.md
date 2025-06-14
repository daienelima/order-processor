
# 🧾 Order Processor

Este é um microserviço em Java 21 com Spring Boot responsável por processar pedidos recebidos via Kafka, 
calcular o valor total dos produtos e persistir no banco PostgreSQL.

---

## ⚙️ Tecnologias Utilizadas

- Java 21
- Spring Boot
- Maven
- Apache Kafka
- PostgreSQL
- Docker + Docker Compose
- MapStruct
- JPA/Hibernate

---

## 🚀 Como executar localmente

### Pré-requisitos

- Docker e Docker Compose instalados
- Java 21 e Maven instalados (caso deseje rodar fora do Docker)

### Subindo apenas Kafka + Postgres

```bash
docker-compose up -d kafka zookeeper postgres
```

> Isso deixa sua aplicação livre para ser executada localmente via IDE.

### Rodando a aplicação

```bash
./mvnw spring-boot:run
```

Ou para buildar o JAR:

```bash
./mvnw clean package -DskipTests
java -jar target/processor.jar
```

### Subindo tudo via Docker

```bash
docker-compose up --build
```

---

## 📦 Estrutura do Projeto

```text
src/
├── main/java/com/order/processor/
│   ├── adapter/          # Entidades e interfaces externas (JPA, Kafka)
│   ├── application/      # Regras de negócio
│   ├── config/           # Configurações Spring, Kafka, DB
│   └── OrderProcessorApplication.java
├── resources/
│   └── application.properties
tools/
│   └── roducer.py  # Script Python para enviar mensagens de teste
```

---

## 🧪 Testando com Kafka

Use o script Python:

```bash
cd tools
python producer.py
```

> Ele envia uma mensagem simulando um pedido para o tópico Kafka `tp-pedidos-recebidos`.

---

## 📥 Mensagem Kafka esperada

```json
{
  "codigoExterno": "IDEMP-abc123...",
  "dataCriacao": "2025-06-14T14:00:00",
  "produtos": [
    { "nome": "Teclado", "precoUnitario": 150.00, "quantidade": 1 },
    { "nome": "Mouse", "precoUnitario": 90.50, "quantidade": 2 }
  ]
}
```

---

## 📌 Observações

- Os pedidos são identificados de forma idempotente com base em `dataCriacao + valorTotal`.
- Os produtos são vinculados via JPA com `pedido_id`.
- O sistema é tolerante a duplicidade e ignora pedidos já processados com `codigoExterno` repetido.

---

## 🧑‍💻 Desenvolvido por

[Daiene Lima](https://github.com/daienelima)
