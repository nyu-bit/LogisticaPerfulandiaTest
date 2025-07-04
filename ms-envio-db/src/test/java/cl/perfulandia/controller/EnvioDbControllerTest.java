package cl.perfulandia.controller;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import cl.perfulandia.repository.EnvioRepository;
import cl.perfulandia.model.entity.Envio;
import cl.perfulandia.model.dto.EnvioDTO;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.*;

class EnvioDbControllerTest {

    @Mock
    private EnvioRepository envioRepository;

    @InjectMocks
    private EnvioDbController envioDbController;

    public EnvioDbControllerTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testFindAll() {
        Envio envio = new Envio();
        envio.setId(1L);
        envio.setEstado("PENDIENTE");
        
        when(envioRepository.findAll()).thenReturn(Arrays.asList(envio));
        
        ResponseEntity<List<EnvioDTO>> resultado = envioDbController.findAll();
        
        assertEquals(200, resultado.getStatusCodeValue());
        assertEquals(1, resultado.getBody().size());
        assertEquals("PENDIENTE", resultado.getBody().get(0).getEstado());
    }

    @Test
    void testFindById() {
        Envio envio = new Envio();
        envio.setId(1L);
        envio.setEstado("PENDIENTE");
        
        when(envioRepository.findById(1L)).thenReturn(Optional.of(envio));
        
        ResponseEntity<EnvioDTO> resultado = envioDbController.findById(1L);
        
        assertEquals(200, resultado.getStatusCodeValue());
        assertEquals(1L, resultado.getBody().getId());
    }
}
