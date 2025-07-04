package cl.perfulandia.service;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import cl.perfulandia.client.PedidoDbClient;
import cl.perfulandia.model.dto.PedidoDTO;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.*;

class PedidoServiceTest {

    @Mock
    private PedidoDbClient pedidoDbClient;

    @InjectMocks
    private PedidoService pedidoService;

    public PedidoServiceTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testFindAll() {
        PedidoDTO pedido = new PedidoDTO();
        pedido.setId(1L);
        pedido.setClienteId(100L);
        pedido.setEstado("PENDIENTE");
        
        when(pedidoDbClient.findAll()).thenReturn(Arrays.asList(pedido));
        
        List<PedidoDTO> resultado = pedidoService.findAll();
        
        assertEquals(1, resultado.size());
        assertEquals("PENDIENTE", resultado.get(0).getEstado());
    }

    @Test
    void testFindById() {
        PedidoDTO pedido = new PedidoDTO();
        pedido.setId(1L);
        pedido.setClienteId(100L);
        
        when(pedidoDbClient.findById(1L)).thenReturn(pedido);
        
        PedidoDTO resultado = pedidoService.findById(1L);
        
        assertEquals(1L, resultado.getId());
        assertEquals(100L, resultado.getClienteId());
    }
}
