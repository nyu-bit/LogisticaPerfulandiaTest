package cl.perfulandia.controller;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import cl.perfulandia.repository.ProveedorRepository;
import cl.perfulandia.model.entity.Proveedor;
import cl.perfulandia.model.dto.ProveedorDTO;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.*;

class ProveedorDbControllerTest {

    @Mock
    private ProveedorRepository proveedorRepository;

    @InjectMocks
    private ProveedorDbController proveedorDbController;

    public ProveedorDbControllerTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testFindAll() {
        Proveedor proveedor = new Proveedor();
        proveedor.setId(1L);
        proveedor.setNombre("Proveedor Test");
        
        when(proveedorRepository.findAll()).thenReturn(Arrays.asList(proveedor));
        
        ResponseEntity<List<ProveedorDTO>> resultado = proveedorDbController.findAll();
        
        assertEquals(200, resultado.getStatusCodeValue());
        assertEquals(1, resultado.getBody().size());
        assertEquals("Proveedor Test", resultado.getBody().get(0).getNombre());
    }

    @Test
    void testFindById() {
        Proveedor proveedor = new Proveedor();
        proveedor.setId(1L);
        proveedor.setNombre("Proveedor Test");
        
        when(proveedorRepository.findById(1L)).thenReturn(Optional.of(proveedor));
        
        ResponseEntity<ProveedorDTO> resultado = proveedorDbController.findById(1L);
        
        assertEquals(200, resultado.getStatusCodeValue());
        assertEquals(1L, resultado.getBody().getId());
    }
}
