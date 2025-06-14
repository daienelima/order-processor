package com.order.processor.adapter.in.controller.response;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public record PedidoResponse(
    String codigoExterno,
    LocalDateTime dataCriacao,
    BigDecimal valorTotal,
    String status,
    List<ProdutoResponse> produtos
) {}