package cl.perfulandia.service;

import cl.perfulandia.client.EnvioDbClient;
import cl.perfulandia.model.dto.EnvioDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EnvioServiceTest {

    @Mock
    private EnvioDbClient envioDbClient;

    @InjectMocks
    private EnvioService envioService;

    private EnvioDTO envioDTO;

    @BeforeEach
    void setUp() {
        envioDTO = new EnvioDTO();
        envioDTO.setId(1L);
        envioDTO.setPedidoId(100L);
        envioDTO.setDireccionDestino("Av. Principal 123");
        envioDTO.setEstado("PENDIENTE");
        envioDTO.setFechaEnvio("2024-01-15T10:30:00");
    }

    @Test
    void findAll_DeberiaRetornarListaDeEnvios() {
        // Given
        List<EnvioDTO> enviosEsperados = Arrays.asList(envioDTO);
        when(envioDbClient.findAll()).thenReturn(enviosEsperados);

        // When
        List<EnvioDTO> resultado = envioService.findAll();

        // Then
        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals(envioDTO.getId(), resultado.get(0).getId());
        verify(envioDbClient, times(1)).findAll();
    }

    @Test
    void findById_ConIdValido_DeberiaRetornarEnvio() {
        // Given
        Long id = 1L;
        when(envioDbClient.findById(id)).thenReturn(envioDTO);

        // When
        EnvioDTO resultado = envioService.findById(id);

        // Then
        assertNotNull(resultado);
        assertEquals(envioDTO.getId(), resultado.getId());
        assertEquals(envioDTO.getDireccionDestino(), resultado.getDireccionDestino());
        verify(envioDbClient, times(1)).findById(id);
    }

    @Test
    void findByPedidoId_ConPedidoIdValido_DeberiaRetornarEnvios() {
        // Given
        Long pedidoId = 100L;
        List<EnvioDTO> enviosEsperados = Arrays.asList(envioDTO);
        when(envioDbClient.findByPedidoId(pedidoId)).thenReturn(enviosEsperados);

        // When
        List<EnvioDTO> resultado = envioService.findByPedidoId(pedidoId);

        // Then
        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals(pedidoId, resultado.get(0).getPedidoId());
        verify(envioDbClient, times(1)).findByPedidoId(pedidoId);
    }

    @Test
    void findByEstado_ConEstadoValido_DeberiaRetornarEnvios() {
        // Given
        String estado = "PENDIENTE";
        List<EnvioDTO> enviosEsperados = Arrays.asList(envioDTO);
        when(envioDbClient.findByEstado(estado)).thenReturn(enviosEsperados);

        // When
        List<EnvioDTO> resultado = envioService.findByEstado(estado);

        // Then
        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals(estado, resultado.get(0).getEstado());
        verify(envioDbClient, times(1)).findByEstado(estado);
    }

    @Test
    void save_ConEnvioValido_DeberiaGuardarEnvio() {
        // Given
        EnvioDTO nuevoEnvio = new EnvioDTO();
        nuevoEnvio.setDireccionDestino("Av. Nueva 456");
        
        EnvioDTO envioGuardado = new EnvioDTO();
        envioGuardado.setId(2L);
        envioGuardado.setDireccionDestino("Av. Nueva 456");
        envioGuardado.setEstado("PENDIENTE");
        
        when(envioDbClient.save(any(EnvioDTO.class))).thenReturn(envioGuardado);

        // When
        EnvioDTO resultado = envioService.save(nuevoEnvio);

        // Then
        assertNotNull(resultado);
        assertEquals("PENDIENTE", nuevoEnvio.getEstado());
        assertNotNull(nuevoEnvio.getFechaEnvio());
        verify(envioDbClient, times(1)).save(nuevoEnvio);
    }

    @Test
    void save_ConDireccionVacia_DeberiaLanzarExcepcion() {
        // Given
        EnvioDTO envioSinDireccion = new EnvioDTO();
        envioSinDireccion.setDireccionDestino("");

        // When & Then
        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> envioService.save(envioSinDireccion)
        );
        
        assertEquals("La dirección de destino es obligatoria", exception.getMessage());
        verify(envioDbClient, never()).save(any());
    }

    @Test
    void save_ConDireccionNula_DeberiaLanzarExcepcion() {
        // Given
        EnvioDTO envioSinDireccion = new EnvioDTO();
        envioSinDireccion.setDireccionDestino(null);

        // When & Then
        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> envioService.save(envioSinDireccion)
        );
        
        assertEquals("La dirección de destino es obligatoria", exception.getMessage());
        verify(envioDbClient, never()).save(any());
    }

    @Test
    void crearEnvioAutomatico_ConDatosValidos_DeberiaCrearEnvio() {
        // Given
        Long pedidoId = 200L;
        Long clienteId = 300L;
        
        EnvioDTO envioCreado = new EnvioDTO();
        envioCreado.setId(3L);
        envioCreado.setPedidoId(pedidoId);
        envioCreado.setEstado("PENDIENTE");
        
        when(envioDbClient.save(any(EnvioDTO.class))).thenReturn(envioCreado);

        // When
        EnvioDTO resultado = envioService.crearEnvioAutomatico(pedidoId, clienteId);

        // Then
        assertNotNull(resultado);
        assertEquals(pedidoId, resultado.getPedidoId());
        verify(envioDbClient, times(1)).save(any(EnvioDTO.class));
    }
}
