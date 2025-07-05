package cl.perfulandia.controller;

import cl.perfulandia.model.dto.ProveedorDTO;
import cl.perfulandia.service.ProveedorService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

class ProveedorBsControllerTest {

    @Mock
    private ProveedorService proveedorService;

    @InjectMocks
    private ProveedorBsController proveedorBsController;

    @BeforeEach
    void configurarMocks() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void deberiaRetornarListaDeProveedores() {
        // Arrnge
        ProveedorDTO proveedor = new ProveedorDTO();
        proveedor.setId(1L);
        proveedor.setNombre("Proveedor Test");

        when(proveedorService.findAll()).thenReturn(Arrays.asList(proveedor));

        // Act
        List<ProveedorDTO> resultado = proveedorBsController.findAll();

        // Assert
        assertEquals(1, resultado.size());
        assertEquals("Proveedor Test", resultado.get(0).getNombre());
    }

    @Test
    void deberiaRetornarProveedorPorId() {
        // Arrange
        ProveedorDTO proveedor = new ProveedorDTO();
        proveedor.setId(1L);
        proveedor.setNombre("Proveedor Test");

        when(proveedorService.findById(1L)).thenReturn(proveedor);

        // Act
        ResponseEntity<ProveedorDTO> respuesta = proveedorBsController.findById(1L);

        // Assert
        assertEquals(200, respuesta.getStatusCodeValue());
        assertEquals(1L, respuesta.getBody().getId());
        assertEquals("Proveedor Test", respuesta.getBody().getNombre());
    }

    @Test
    void deberiaRetornar404_SiProveedorNoExiste() {
        // Arrange
        when(proveedorService.findById(99L)).thenReturn(null);

        // Act
        ResponseEntity<ProveedorDTO> respuesta = proveedorBsController.findById(99L);

        // Assert
        assertEquals(404, respuesta.getStatusCodeValue());
    }
}
