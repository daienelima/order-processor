package com.order.processor.adapter.in.controller;

import com.order.processor.adapter.in.controller.mapper.ResponseMapper;
import com.order.processor.adapter.in.controller.response.PedidoResponse;
import com.order.processor.application.port.in.ListarPedidosInputPort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/pedidos")
public class PedidosController {
    private final ListarPedidosInputPort listarPedidosInputPort;

    public PedidosController(ListarPedidosInputPort listarPedidosInputPort) {
        this.listarPedidosInputPort = listarPedidosInputPort;
    }

    @GetMapping
    public ResponseEntity<List<PedidoResponse>> litarPedidos() {
        var pedidos = listarPedidosInputPort.listarPedidos();
        return ResponseEntity.ok()
                .body(pedidos.stream()
                        .map(ResponseMapper.INSTANCE::toPedidoResponse)
                        .toList());
    }
}
