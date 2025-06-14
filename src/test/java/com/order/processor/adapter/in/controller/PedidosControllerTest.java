package com.order.processor.adapter.in.controller;

import com.order.processor.adapter.in.controller.response.PedidoResponse;
import com.order.processor.application.core.domain.Pedido;
import com.order.processor.application.port.in.ListarPedidosInputPort;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PedidosControllerTest {

    @Mock
    private ListarPedidosInputPort listarPedidosInputPort;
    @InjectMocks
    private PedidosController pedidosController;

    @Test
    void deveListarPedidosERetornarOk() {
        ListarPedidosInputPort inputPort = mock(ListarPedidosInputPort.class);
        PedidosController controller = new PedidosController(inputPort);

        var pedido = mock(Pedido.class);
        when(inputPort.listarPedidos()).thenReturn(List.of(pedido));

        ResponseEntity<List<PedidoResponse>> response = controller.litarPedidos();

        assertThat(response.getStatusCodeValue()).isEqualTo(200);
        assertThat(response.getBody()).isNotNull();
        verify(inputPort, times(1)).listarPedidos();
    }
}