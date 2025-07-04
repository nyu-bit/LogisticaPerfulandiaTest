package cl.perfulandia.service;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import cl.perfulandia.client.ProveedorDbClient;
import cl.perfulandia.model.dto.ProveedorDTO;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.*;

class ProveedorServiceTest {

    @Mock
    private ProveedorDbClient proveedorDbClient;

    @InjectMocks
    private ProveedorService proveedorService;

    public ProveedorServiceTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testFindAll() {
        ProveedorDTO proveedor = new ProveedorDTO();
        proveedor.setId(1L);
        proveedor.setNombre("Proveedor Test");
        proveedor.setEstado("ACTIVO");
        
        when(proveedorDbClient.findAll()).thenReturn(Arrays.asList(proveedor));
        
        List<ProveedorDTO> resultado = proveedorService.findAll();
        
        assertEquals(1, resultado.size());
        assertEquals("Proveedor Test", resultado.get(0).getNombre());
    }

    @Test
    void testFindById() {
        ProveedorDTO proveedor = new ProveedorDTO();
        proveedor.setId(1L);
        proveedor.setNombre("Proveedor Test");
        
        when(proveedorDbClient.findById(1L)).thenReturn(proveedor);
        
        ProveedorDTO resultado = proveedorService.findById(1L);
        
        assertEquals(1L, resultado.getId());
        assertEquals("Proveedor Test", resultado.getNombre());
    }
}
