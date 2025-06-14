package com.order.processor.adapter.out;

import com.order.processor.adapter.out.mapper.EntityMapper;
import com.order.processor.adapter.out.repository.PedidoEntityRepository;
import com.order.processor.application.core.domain.Pedido;
import com.order.processor.application.port.ProcessarPedidoOutputPort;
import org.springframework.stereotype.Service;

@Service
public class RepositoryAdapter implements ProcessarPedidoOutputPort {

    private  final PedidoEntityRepository pedidoEntityRepository;

    public RepositoryAdapter(PedidoEntityRepository pedidoEntityRepository) {
        this.pedidoEntityRepository = pedidoEntityRepository;
    }


    @Override
    public boolean existePedidoPorCodigoExterno(String codigoExterno) {
        return pedidoEntityRepository.existsByCodigoExterno(codigoExterno);
    }

    @Override
    public void salvarPedido(Pedido pedido) {
        var pedidoEntity = EntityMapper.INSTANCE.toPedidoEntity(pedido);
        pedidoEntityRepository.save(pedidoEntity);
    }
}
