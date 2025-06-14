package com.order.processor.adapter.in.controller.mapper;

import com.order.processor.adapter.in.controller.response.PedidoResponse;
import com.order.processor.application.core.domain.Pedido;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ResponseMapper {
    ResponseMapper INSTANCE = Mappers.getMapper(ResponseMapper.class);

    PedidoResponse toPedidoResponse(Pedido pedido);

}
