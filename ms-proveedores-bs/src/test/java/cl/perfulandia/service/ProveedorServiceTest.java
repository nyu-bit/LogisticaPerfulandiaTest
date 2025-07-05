package cl.perfulandia.service;

import cl.perfulandia.client.ProveedorDbClient;
import cl.perfulandia.model.dto.ProveedorDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

class ProveedorServiceTest {

    @Mock
    private ProveedorDbClient proveedorDbClient;

    @InjectMocks
    private ProveedorService proveedorService;

    @BeforeEach
    void configurar() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void deberiaRetornarListaDeProveedores() {
        // Arrange
        ProveedorDTO proveedor = new ProveedorDTO();
        proveedor.setId(1L);
        proveedor.setNombre("Proveedor Test");
        proveedor.setEstado("ACTIVO");

        when(proveedorDbClient.findAll()).thenReturn(Arrays.asList(proveedor));

        // Act
        List<ProveedorDTO> resultado = proveedorService.findAll();

        // Assert
        assertEquals(1, resultado.size());
        assertEquals("Proveedor Test", resultado.get(0).getNombre());
        assertEquals("ACTIVO", resultado.get(0).getEstado());
    }

    @Test
    void deberiaRetornarProveedorPorId() {
        // Arrange
        ProveedorDTO proveedor = new ProveedorDTO();
        proveedor.setId(1L);
        proveedor.setNombre("Proveedor Test");

        when(proveedorDbClient.findById(1L)).thenReturn(proveedor);

        // Act
        ProveedorDTO resultado = proveedorService.findById(1L);

        // Assert
        assertEquals(1L, resultado.getId());
        assertEquals("Proveedor Test", resultado.getNombre());
    }
}
