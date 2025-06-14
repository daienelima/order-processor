package com.order.processor.adapter.in.controller;

import com.order.processor.adapter.out.entities.PedidoEntity;
import com.order.processor.adapter.out.entities.ProdutoEntity;
import com.order.processor.adapter.out.enums.StatusPedido;
import com.order.processor.adapter.out.repositories.PedidoEntityRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class PedidosControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private PedidoEntityRepository pedidoRepository;

    @BeforeEach
    void setup() {
        pedidoRepository.deleteAll();

        PedidoEntity pedido = new PedidoEntity();
        pedido.setCodigoExterno("IDEMP-123");
        pedido.setDataCriacao(LocalDateTime.now());
        pedido.setStatus(StatusPedido.RECEBIDO);
        pedido.setValorTotal(new BigDecimal("331.00"));

        ProdutoEntity p1 = new ProdutoEntity(null, "Teclado", new BigDecimal("150.00"), 1, new BigDecimal("150.00") ,pedido);
        ProdutoEntity p2 = new ProdutoEntity(null, "Mouse", new BigDecimal("90.50"), 2, new BigDecimal("181.00"),pedido);

        pedido.setProdutos(List.of(p1, p2));

        pedidoRepository.save(pedido);
    }

    @Test
    void deveRetornarListaDePedidosComProdutos() throws Exception {
        mockMvc.perform(get("/api/pedidos")
                .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$", hasSize(1)))
            .andExpect(jsonPath("$[0].codigoExterno", is("IDEMP-123")))
            .andExpect(jsonPath("$[0].produtos", hasSize(2)))
            .andExpect(jsonPath("$[0].produtos[0].nome", anyOf(is("Teclado"), is("Mouse"))));
    }
}