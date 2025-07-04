package cl.perfulandia.controller;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import cl.perfulandia.repository.PedidoRepository;
import cl.perfulandia.model.entity.Pedido;
import cl.perfulandia.model.dto.PedidoDTO;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.*;

class PedidoDbControllerTest {

    @Mock
    private PedidoRepository pedidoRepository;

    @InjectMocks
    private PedidoDbController pedidoDbController;

    public PedidoDbControllerTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testFindAll() {
        Pedido pedido = new Pedido();
        pedido.setId(1L);
        pedido.setEstado("PENDIENTE");
        
        when(pedidoRepository.findAll()).thenReturn(Arrays.asList(pedido));
        
        ResponseEntity<List<PedidoDTO>> resultado = pedidoDbController.findAll();
        
        assertEquals(200, resultado.getStatusCodeValue());
        assertEquals(1, resultado.getBody().size());
        assertEquals("PENDIENTE", resultado.getBody().get(0).getEstado());
    }

    @Test
    void testFindById() {
        Pedido pedido = new Pedido();
        pedido.setId(1L);
        pedido.setEstado("PENDIENTE");
        
        when(pedidoRepository.findById(1L)).thenReturn(Optional.of(pedido));
        
        ResponseEntity<PedidoDTO> resultado = pedidoDbController.findById(1L);
        
        assertEquals(200, resultado.getStatusCodeValue());
        assertEquals(1L, resultado.getBody().getId());
    }
}
