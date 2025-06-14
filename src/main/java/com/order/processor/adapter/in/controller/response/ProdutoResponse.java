package com.order.processor.adapter.in.controller.response;

import java.math.BigDecimal;

public record ProdutoResponse(
    String nome,
    BigDecimal precoUnitario,
    int quantidade,
    BigDecimal valorTotal
) {}