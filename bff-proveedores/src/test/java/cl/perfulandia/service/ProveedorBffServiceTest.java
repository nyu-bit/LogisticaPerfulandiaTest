package cl.perfulandia.service;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import cl.perfulandia.client.ProveedorBsClient;
import cl.perfulandia.client.ProveedorDbClient;
import cl.perfulandia.model.dto.ProveedorDTO;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.*;

class ProveedorBffServiceTest {

    @Mock
    private ProveedorBsClient proveedorBsClient;

    @Mock
    private ProveedorDbClient proveedorDbClient;

    @InjectMocks
    private ProveedorBffService proveedorBffService;

    public ProveedorBffServiceTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testObtenerProveedoresActivos() {
        ProveedorDTO proveedor = new ProveedorDTO();
        proveedor.setId(1L);
        proveedor.setNombre("Proveedor BFF Test");
        
        when(proveedorBsClient.obtenerProveedoresActivos()).thenReturn(Arrays.asList(proveedor));
        
        List<ProveedorDTO> resultado = proveedorBffService.obtenerProveedoresActivos();
        
        assertEquals(1, resultado.size());
        assertEquals("Proveedor BFF Test", resultado.get(0).getNombre());
    }

    @Test
    void testObtenerProveedorPorId() {
        ProveedorDTO proveedor = new ProveedorDTO();
        proveedor.setId(1L);
        proveedor.setNombre("Proveedor Test");
        
        when(proveedorDbClient.findById(1L)).thenReturn(proveedor);
        
        ProveedorDTO resultado = proveedorBffService.obtenerProveedorPorId(1L);
        
        assertEquals(1L, resultado.getId());
        assertEquals("Proveedor Test", resultado.getNombre());
    }
}
