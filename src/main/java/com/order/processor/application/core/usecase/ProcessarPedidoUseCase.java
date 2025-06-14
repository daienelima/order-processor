package com.order.processor.application.core.usecase;

import com.order.processor.adapter.out.enums.StatusPedido;
import com.order.processor.application.core.domain.Pedido;
import com.order.processor.application.core.domain.Produto;
import com.order.processor.application.port.in.ListarPedidosInputPort;
import com.order.processor.application.port.in.ProcessarPedidoInputPort;
import com.order.processor.application.port.out.ListarPedidosOutputPort;
import com.order.processor.application.port.out.ProcessarPedidoOutputPort;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Slf4j
@Service
public class ProcessarPedidoUseCase implements ProcessarPedidoInputPort, ListarPedidosInputPort {
    private  final ProcessarPedidoOutputPort processarPedidoOutputPort;
    private final ListarPedidosOutputPort listarPedidosOutputPort;

    public ProcessarPedidoUseCase(ProcessarPedidoOutputPort processarPedidoOutputPort, ListarPedidosOutputPort listarPedidosOutputPort) {
        this.processarPedidoOutputPort = processarPedidoOutputPort;
        this.listarPedidosOutputPort = listarPedidosOutputPort;
    }

    @Override
    public void processarPedido(Pedido pedido) {
        if(processarPedidoOutputPort.existePedidoPorCodigoExterno(pedido.getCodigoExterno())){
           return;
        }

        pedido.getProdutos().forEach(p ->
                p.setValorTotal(p.getPrecoUnitario().multiply(BigDecimal.valueOf(p.getQuantidade())))
        );

        BigDecimal valorTotal = pedido.getProdutos().stream()
                .map(Produto::getValorTotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        pedido.setValorTotal(valorTotal);
        pedido.setStatus(StatusPedido.RECEBIDO.name());

        log.info("Processando pedido: {}", pedido);
        processarPedidoOutputPort.salvarPedido(pedido);
    }

    @Override
    public List<Pedido> listarPedidos() {
        log.info("[ProcessarPedidoUseCase] listando pedidos");
        return listarPedidosOutputPort.listarPedidos();
    }
}
