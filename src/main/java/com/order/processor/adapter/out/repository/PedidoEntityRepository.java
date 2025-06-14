package com.order.processor.adapter.out.repository;

import com.order.processor.adapter.out.entities.PedidoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PedidoEntityRepository extends JpaRepository<PedidoEntity, Integer> {
    boolean existsByCodigoExterno(String codigoExterno);
}