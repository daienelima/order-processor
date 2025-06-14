package com.order.processor.application.port.in;

import com.order.processor.application.core.domain.Pedido;

public interface ProcessarPedidoInputPort {
    void processarPedido(Pedido pedido);
}
