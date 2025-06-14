package com.order.processor.application.core.usecase;

import com.order.processor.application.core.domain.Pedido;
import com.order.processor.application.core.domain.Produto;
import com.order.processor.application.port.out.ListarPedidosOutputPort;
import com.order.processor.application.port.out.ProcessarPedidoOutputPort;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProcessarPedidoUseCaseTest {

    @Mock
    private ProcessarPedidoOutputPort processarPedidoOutputPort;
    @Mock
    private ListarPedidosOutputPort listarPedidosOutputPort;

    @InjectMocks
    private ProcessarPedidoUseCase useCase;

    @Test
    void deveProcessarPedidoComSucesso() {
        Pedido pedido = new Pedido();
        pedido.setCodigoExterno("123");
        Produto produto = new Produto();
        produto.setPrecoUnitario(new BigDecimal("10.00"));
        produto.setQuantidade(2);
        pedido.setProdutos(List.of(produto));

        when(processarPedidoOutputPort.existePedidoPorCodigoExterno("123")).thenReturn(false);

        useCase.processarPedido(pedido);

        verify(processarPedidoOutputPort).salvarPedido(pedido);
        assert pedido.getValorTotal().compareTo(new BigDecimal("20.00")) == 0;
    }

}