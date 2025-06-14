package com.order.processor.adapter.out.mapper;

import com.order.processor.adapter.out.entities.PedidoEntity;
import com.order.processor.adapter.out.entities.ProdutoEntity;
import com.order.processor.application.core.domain.Pedido;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

@Mapper
public interface EntityMapper {
    EntityMapper INSTANCE = Mappers.getMapper(EntityMapper.class);

    PedidoEntity toPedidoEntity(Pedido pedido);

    @AfterMapping
    default void linkProdutos(@MappingTarget PedidoEntity pedido) {
        if (pedido.getProdutos() != null) {
            for (ProdutoEntity produto : pedido.getProdutos()) {
                produto.setPedido(pedido);
            }
        }
    }
}
