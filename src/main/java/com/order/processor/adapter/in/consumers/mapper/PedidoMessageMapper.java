package com.order.processor.adapter.in.consumers.mapper;

import com.order.processor.adapter.in.consumers.message.PedidoMensagem;
import com.order.processor.application.core.domain.Pedido;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface PedidoMessageMapper {
    PedidoMessageMapper INSTANCE = Mappers.getMapper(PedidoMessageMapper.class);

    Pedido toPedido(PedidoMensagem pedidoMensagem);
}
