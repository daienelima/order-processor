package com.order.processor.adapter.in.consumers.message;

import java.math.BigDecimal;

public record ItemProdutoMensagem(
    String nome,
    BigDecimal precoUnitario,
    int quantidade
) {}
