package cl.perfulandia.controller;

import cl.perfulandia.model.dto.EnvioDTO;
import cl.perfulandia.service.EnvioService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
@WebMvcTest(EnvioBsController.class)
class EnvioBsControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EnvioService envioService;

    @Autowired
    private ObjectMapper objectMapper;

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
    void findAll_DeberiaRetornarListaDeEnvios() throws Exception {
        // Given
        List<EnvioDTO> envios = Arrays.asList(envioDTO);
        when(envioService.findAll()).thenReturn(envios);

        // When & Then
        mockMvc.perform(get("/envios-business"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].direccionDestino").value("Av. Principal 123"));

        verify(envioService, times(1)).findAll();
    }

    @Test
    void findById_ConIdExistente_DeberiaRetornarEnvio() throws Exception {
        // Given
        when(envioService.findById(1L)).thenReturn(envioDTO);

        // When & Then
        mockMvc.perform(get("/envios-business/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.direccionDestino").value("Av. Principal 123"))
                .andExpect(jsonPath("$.estado").value("PENDIENTE"));

        verify(envioService, times(1)).findById(1L);
    }

    @Test
    void findById_ConIdInexistente_DeberiaRetornar404() throws Exception {
        // Given
        when(envioService.findById(999L)).thenReturn(null);

        // When & Then
        mockMvc.perform(get("/envios-business/999"))
                .andExpect(status().isNotFound());

        verify(envioService, times(1)).findById(999L);
    }

    @Test
    void findById_ConExcepcion_DeberiaRetornar404() throws Exception {
        // Given
        when(envioService.findById(1L)).thenThrow(new RuntimeException("Error de BD"));

        // When & Then
        mockMvc.perform(get("/envios-business/1"))
                .andExpect(status().isNotFound());

        verify(envioService, times(1)).findById(1L);
    }

    @Test
    void findByEstado_ConEstadoValido_DeberiaRetornarEnvios() throws Exception {
        // Given
        List<EnvioDTO> envios = Arrays.asList(envioDTO);
        when(envioService.findByEstado("PENDIENTE")).thenReturn(envios);

        // When & Then
        mockMvc.perform(get("/envios-business/estado/PENDIENTE"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].estado").value("PENDIENTE"));

        verify(envioService, times(1)).findByEstado("PENDIENTE");
    }

    @Test
    void findByPedido_ConPedidoIdValido_DeberiaRetornarEnvios() throws Exception {
        // Given
        List<EnvioDTO> envios = Arrays.asList(envioDTO);
        when(envioService.findByPedidoId(100L)).thenReturn(envios);

        // When & Then
        mockMvc.perform(get("/envios-business/pedido/100"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].pedidoId").value(100L));

        verify(envioService, times(1)).findByPedidoId(100L);
    }

    @Test
    void save_ConEnvioValido_DeberiaRetornarEnvioCreado() throws Exception {
        // Given
        EnvioDTO nuevoEnvio = new EnvioDTO();
        nuevoEnvio.setDireccionDestino("Nueva Dirección 789");
        
        EnvioDTO envioGuardado = new EnvioDTO();
        envioGuardado.setId(2L);
        envioGuardado.setDireccionDestino("Nueva Dirección 789");
        envioGuardado.setEstado("PENDIENTE");
        
        when(envioService.save(any(EnvioDTO.class))).thenReturn(envioGuardado);

        // When & Then
        mockMvc.perform(post("/envios-business")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(nuevoEnvio)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(2L))
                .andExpect(jsonPath("$.direccionDestino").value("Nueva Dirección 789"));

        verify(envioService, times(1)).save(any(EnvioDTO.class));
    }

    @Test
    void save_ConExcepcion_DeberiaRetornar400() throws Exception {
        // Given
        EnvioDTO envioInvalido = new EnvioDTO();
        when(envioService.save(any(EnvioDTO.class))).thenThrow(new IllegalArgumentException("Dirección requerida"));

        // When & Then
        mockMvc.perform(post("/envios-business")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(envioInvalido)))
                .andExpect(status().isBadRequest());

        verify(envioService, times(1)).save(any(EnvioDTO.class));
    }

    @Test
    void update_ConEnvioValido_DeberiaRetornarEnvioActualizado() throws Exception {
        // Given
        EnvioDTO envioActualizado = new EnvioDTO();
        envioActualizado.setId(1L);
        envioActualizado.setDireccionDestino("Dirección Actualizada");
        
        when(envioService.update(eq(1L), any(EnvioDTO.class))).thenReturn(envioActualizado);

        // When & Then
        mockMvc.perform(put("/envios-business/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(envioActualizado)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.direccionDestino").value("Dirección Actualizada"));

        verify(envioService, times(1)).update(eq(1L), any(EnvioDTO.class));
    }

    @Test
    void deleteById_ConIdExistente_DeberiaRetornar204() throws Exception {
        // Given
        doNothing().when(envioService).deleteById(1L);

        // When & Then
        mockMvc.perform(delete("/envios-business/1"))
                .andExpect(status().isNoContent());

        verify(envioService, times(1)).deleteById(1L);
    }

    @Test
    void deleteById_ConExcepcion_DeberiaRetornar404() throws Exception {
        // Given
        doThrow(new RuntimeException("Envío no encontrado")).when(envioService).deleteById(999L);

        // When & Then
        mockMvc.perform(delete("/envios-business/999"))
                .andExpect(status().isNotFound());

        verify(envioService, times(1)).deleteById(999L);
    }

    @Test
    void procesarPedidoProcesado_ConDatosValidos_DeberiaCrearEnvio() throws Exception {
        // Given
        PedidoProcesadoRequest request = new PedidoProcesadoRequest();
        request.setPedidoId(100L);
        request.setClienteId(200L);
        
        when(envioService.crearEnvioAutomatico(100L, 200L)).thenReturn(envioDTO);

        // When & Then
        mockMvc.perform(post("/envios-business/procesar-pedido")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.pedidoId").value(100L));

        verify(envioService, times(1)).crearEnvioAutomatico(100L, 200L);
    }
}
