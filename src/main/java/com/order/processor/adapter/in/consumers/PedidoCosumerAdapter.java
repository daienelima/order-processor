package com.order.processor.adapter.in.consumers;

import com.order.processor.adapter.in.consumers.mapper.PedidoMessageMapper;
import com.order.processor.adapter.in.consumers.message.PedidoMensagem;
import com.order.processor.application.port.in.ProcessarPedidoInputPort;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class PedidoCosumerAdapter {

    private final ProcessarPedidoInputPort processarPedidoInputPort;

    public PedidoCosumerAdapter(ProcessarPedidoInputPort processarPedidoInputPort) {
        this.processarPedidoInputPort = processarPedidoInputPort;
    }

    @KafkaListener(
            topics = "${kafka.topic.pedidos-receiver}",
            groupId = "${spring.kafka.consumer.group-id}"
    )
    public void pedidosRecebidos(PedidoMensagem pedidoMensagem){
        log.info("[PedidoCosumerAdapter] pedido recebido {}", pedidoMensagem);

        var pedido = PedidoMessageMapper.INSTANCE.toPedido(pedidoMensagem);

        processarPedidoInputPort.processarPedido(pedido);
    }
}
