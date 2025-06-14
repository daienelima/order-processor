package com.order.processor.application.port.in;

import com.order.processor.application.core.domain.Pedido;

import java.util.List;

public interface ListarPedidosInputPort {
    List<Pedido> listarPedidos();
}
