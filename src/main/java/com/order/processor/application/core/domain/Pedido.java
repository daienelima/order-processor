package com.order.processor.application.core.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Pedido{
    private String codigoExterno;
    private LocalDateTime dataCriacao;
    private List<Produto> produtos;
    private BigDecimal valorTotal;
    private String status;
}