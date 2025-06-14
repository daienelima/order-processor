package com.order.processor.adapter.in.consumers.message;

import java.time.LocalDateTime;
import java.util.List;

public record PedidoMensagem(
        String codigoExterno,
        LocalDateTime dataCriacao,
        List<ItemProdutoMensagem> produtos
) {}
