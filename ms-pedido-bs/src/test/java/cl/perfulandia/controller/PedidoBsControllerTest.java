package cl.perfulandia.controller;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import cl.perfulandia.service.PedidoService;
import cl.perfulandia.model.dto.PedidoDTO;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.*;

class PedidoBsControllerTest {

    @Mock
    private PedidoService pedidoService;

    @InjectMocks
    private PedidoBsController pedidoBsController;

    public PedidoBsControllerTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testObtenerTodos() {
        PedidoDTO pedido = new PedidoDTO();
        pedido.setId(1L);
        pedido.setEstado("PENDIENTE");
        
        when(pedidoService.findAll()).thenReturn(Arrays.asList(pedido));
        
        List<PedidoDTO> resultado = pedidoBsController.obtenerTodos();
        
        assertEquals(1, resultado.size());
        assertEquals("PENDIENTE", resultado.get(0).getEstado());
    }

    @Test
    void testObtenerPorId() {
        PedidoDTO pedido = new PedidoDTO();
        pedido.setId(1L);
        pedido.setEstado("PENDIENTE");
        
        when(pedidoService.findById(1L)).thenReturn(pedido);
        
        ResponseEntity<PedidoDTO> resultado = pedidoBsController.obtenerPorId(1L);
        
        assertEquals(200, resultado.getStatusCodeValue());
        assertEquals(1L, resultado.getBody().getId());
    }
}
