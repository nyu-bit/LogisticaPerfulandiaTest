package cl.perfulandia.controller;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import cl.perfulandia.service.ProveedorBffService;
import cl.perfulandia.model.dto.ProveedorDTO;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.*;

class ProveedorBffControllerTest {

    @Mock
    private ProveedorBffService proveedorBffService;

    @InjectMocks
    private ProveedorBffController proveedorBffController;

    public ProveedorBffControllerTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testObtenerTodos() {
        ProveedorDTO proveedor = new ProveedorDTO();
        proveedor.setId(1L);
        proveedor.setNombre("Proveedor BFF");
        
        when(proveedorBffService.obtenerTodosProveedores()).thenReturn(Arrays.asList(proveedor));
        
        List<ProveedorDTO> resultado = proveedorBffController.obtenerTodos();
        
        assertEquals(1, resultado.size());
        assertEquals("Proveedor BFF", resultado.get(0).getNombre());
    }

    @Test
    void testObtenerPorId() {
        ProveedorDTO proveedor = new ProveedorDTO();
        proveedor.setId(1L);
        proveedor.setNombre("Proveedor BFF");
        
        when(proveedorBffService.obtenerProveedorPorId(1L)).thenReturn(proveedor);
        
        ResponseEntity<ProveedorDTO> resultado = proveedorBffController.obtenerPorId(1L);
        
        assertEquals(200, resultado.getStatusCodeValue());
        assertEquals(1L, resultado.getBody().getId());
    }
}
