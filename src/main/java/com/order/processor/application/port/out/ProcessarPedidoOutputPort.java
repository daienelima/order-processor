package com.order.processor.application.port.out;

import com.order.processor.application.core.domain.Pedido;

public interface ProcessarPedidoOutputPort {
    boolean existePedidoPorCodigoExterno(String codigoExterno);
   void salvarPedido(Pedido pedido);
}
