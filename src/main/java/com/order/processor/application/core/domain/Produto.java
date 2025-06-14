package com.order.processor.application.core.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Produto{
    private String nome;
    private BigDecimal precoUnitario;
    private BigDecimal valorTotal;
    private int quantidade;
}