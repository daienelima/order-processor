package com.order.processor.application.port.out;

import com.order.processor.application.core.domain.Pedido;

import java.util.List;

public interface ListarPedidosOutputPort {
    List<Pedido> listarPedidos();
}
