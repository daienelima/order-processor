package com.order.processor.application.core.usecase;

import com.order.processor.adapter.out.enums.StatusPedido;
import com.order.processor.application.core.domain.Pedido;
import com.order.processor.application.port.ProcessarPedidoInputPort;
import com.order.processor.application.port.ProcessarPedidoOutputPort;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Slf4j
@Service
public class ProcessarPedidoUseCase implements ProcessarPedidoInputPort {
    private  final ProcessarPedidoOutputPort processarPedidoOutputPort;

    public ProcessarPedidoUseCase(ProcessarPedidoOutputPort processarPedidoOutputPort) {
        this.processarPedidoOutputPort = processarPedidoOutputPort;
    }

    @Override
    public void processarPedido(Pedido pedido) {
        if(processarPedidoOutputPort.existePedidoPorCodigoExterno(pedido.getCodigoExterno())){
           return;
        }

        BigDecimal valorTotal = pedido.getProdutos().stream()
                .map(p -> p.getPrecoUnitario().multiply(BigDecimal.valueOf(p.getQuantidade())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        pedido.setValorTotal(valorTotal);
        pedido.setStatus(StatusPedido.RECEBIDO.name());

        log.info("Processando pedido: {}", pedido);
        processarPedidoOutputPort.salvarPedido(pedido);
    }
}
