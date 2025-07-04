package cl.perfulandia.controller;

import cl.perfulandia.model.dto.EnvioDTO;
import cl.perfulandia.service.EnvioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/envios-business")
@Tag(name = "Envíos Business", description = "API de lógica de negocio para gestión de envíos")
public class EnvioBsController {

    @Autowired
    private EnvioService envioService;

    @GetMapping
    @Operation(summary = "Obtener todos los envíos", description = "Retorna una lista de todos los envíos con lógica de negocio aplicada")
    @ApiResponse(responseCode = "200", description = "Lista de envíos obtenida exitosamente")
    public List<EnvioDTO> findAll() {
        return envioService.findAll();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener envío por ID", description = "Retorna un envío específico aplicando lógica de negocio")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Envío encontrado"),
        @ApiResponse(responseCode = "404", description = "Envío no encontrado")
    })
    public ResponseEntity<EnvioDTO> findById(
            @Parameter(description = "ID del envío a buscar") @PathVariable Long id) {
        try {
            EnvioDTO envio = envioService.findById(id);
            return envio != null ? ResponseEntity.ok(envio) : ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/estado/{estado}")
    public List<EnvioDTO> findByEstado(@PathVariable String estado) {
        return envioService.findByEstado(estado);
    }

    @GetMapping("/pedido/{pedidoId}")
    public List<EnvioDTO> findByPedido(@PathVariable Long pedidoId) {
        return envioService.findByPedidoId(pedidoId);
    }

    @PostMapping
    public ResponseEntity<EnvioDTO> save(@RequestBody EnvioDTO envio) {
        try {
            EnvioDTO nuevoEnvio = envioService.save(envio);
            return ResponseEntity.ok(nuevoEnvio);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<EnvioDTO> update(@PathVariable Long id, @RequestBody EnvioDTO envio) {
        try {
            EnvioDTO envioActualizado = envioService.update(id, envio);
            return ResponseEntity.ok(envioActualizado);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/{envioId}/asignar-transportista")
    public ResponseEntity<EnvioDTO> asignarTransportista(@PathVariable Long envioId, @RequestBody AsignarTransportistaRequest request) {
        try {
            EnvioDTO envio = envioService.asignarTransportista(envioId, request.getTransportistaId());
            return ResponseEntity.ok(envio);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/{envioId}/entregar")
    public ResponseEntity<EnvioDTO> marcarComoEntregado(@PathVariable Long envioId) {
        try {
            EnvioDTO envio = envioService.marcarComoEntregado(envioId);
            return ResponseEntity.ok(envio);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/procesar-pedido")
    public ResponseEntity<EnvioDTO> procesarPedidoProcesado(@RequestBody PedidoProcesadoRequest request) {
        try {
            EnvioDTO envio = envioService.crearEnvioAutomatico(request.getPedidoId(), request.getClienteId());
            return ResponseEntity.ok(envio);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        try {
            envioService.deleteById(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
}

@lombok.Data
@lombok.NoArgsConstructor
@lombok.AllArgsConstructor
class AsignarTransportistaRequest {
    private Long transportistaId;
}

@lombok.Data
@lombok.NoArgsConstructor
@lombok.AllArgsConstructor
class PedidoProcesadoRequest {
    private Long pedidoId;
    private Long clienteId;
    private Long productoId;
}
