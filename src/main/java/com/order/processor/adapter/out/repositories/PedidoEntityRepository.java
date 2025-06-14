package com.order.processor.adapter.out.repositories;

import com.order.processor.adapter.out.entities.PedidoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PedidoEntityRepository extends JpaRepository<PedidoEntity, Integer> {
    boolean existsByCodigoExterno(String codigoExterno);
}