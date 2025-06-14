package com.order.processor.adapter.out;

import com.order.processor.adapter.out.mapper.EntityMapper;
import com.order.processor.adapter.out.repositories.PedidoEntityRepository;
import com.order.processor.application.core.domain.Pedido;
import com.order.processor.application.port.out.ListarPedidosOutputPort;
import com.order.processor.application.port.out.ProcessarPedidoOutputPort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RepositoryAdapter implements ProcessarPedidoOutputPort, ListarPedidosOutputPort {

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

    @Override
    public List<Pedido> listarPedidos() {
        return pedidoEntityRepository.findAll()
                .stream()
                .map(EntityMapper.INSTANCE::toPedido)
                .toList();
    }
}
