package cl.perfulandia.controller;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import cl.perfulandia.service.ProveedorService;
import cl.perfulandia.model.dto.ProveedorDTO;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.*;

class ProveedorBsControllerTest {

    @Mock
    private ProveedorService proveedorService;

    @InjectMocks
    private ProveedorBsController proveedorBsController;

    public ProveedorBsControllerTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testFindAll() {
        ProveedorDTO proveedor = new ProveedorDTO();
        proveedor.setId(1L);
        proveedor.setNombre("Proveedor Test");
        
        when(proveedorService.findAll()).thenReturn(Arrays.asList(proveedor));
        
        List<ProveedorDTO> resultado = proveedorBsController.findAll();
        
        assertEquals(1, resultado.size());
        assertEquals("Proveedor Test", resultado.get(0).getNombre());
    }

    @Test
    void testFindById() {
        ProveedorDTO proveedor = new ProveedorDTO();
        proveedor.setId(1L);
        proveedor.setNombre("Proveedor Test");
        
        when(proveedorService.findById(1L)).thenReturn(proveedor);
        
        ResponseEntity<ProveedorDTO> resultado = proveedorBsController.findById(1L);
        
        assertEquals(200, resultado.getStatusCodeValue());
        assertEquals(1L, resultado.getBody().getId());
    }
}
