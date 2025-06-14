package com.order.processor.adapter.in.consumers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.order.processor.adapter.out.entities.PedidoEntity;
import com.order.processor.adapter.out.repositories.PedidoEntityRepository;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;
import org.springframework.test.annotation.DirtiesContext;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@DirtiesContext
@EmbeddedKafka(partitions = 1, topics = "tp-pedidos-recebidos", brokerProperties = {
        "listeners=PLAINTEXT://localhost:9092", "port=9092"
})
public class PedidoConsumerIntegrationTest {

    @Autowired
    private PedidoEntityRepository pedidoRepository;

    @Autowired
    private ObjectMapper objectMapper;

    private KafkaTemplate<String, Object> createKafkaTemplate() {
        Map<String, Object> config = Map.of(
            ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092",
            ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class,
            ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class
        );
        ProducerFactory<String, Object> factory = new DefaultKafkaProducerFactory<>(config);
        return new KafkaTemplate<>(factory);
    }

    @Test
    public void deveConsumirMensagemKafkaEPersistirPedido() throws Exception {
        KafkaTemplate<String, Object> kafkaTemplate = createKafkaTemplate();

        String codigoExterno = "IDEMP-" + UUID.randomUUID();
        var mensagem = Map.of(
            "codigoExterno", codigoExterno,
            "dataCriacao", LocalDateTime.now().toString(),
            "produtos", List.of(
                Map.of("nome", "Teclado", "precoUnitario", new BigDecimal("150.00"), "quantidade", 1),
                Map.of("nome", "Mouse", "precoUnitario", new BigDecimal("90.50"), "quantidade", 2)
            )
        );

        kafkaTemplate.send("tp-pedidos-recebidos", "pedido-test", mensagem);
        kafkaTemplate.flush();

        Thread.sleep(3000);

        List<PedidoEntity> pedidos = pedidoRepository.findAll();
        assertThat(pedidos).anyMatch(p -> p.getCodigoExterno().equals(codigoExterno));
    }
}