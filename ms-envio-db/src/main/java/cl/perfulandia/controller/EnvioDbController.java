package cl.perfulandia.controller;

import cl.perfulandia.model.dto.EnvioDTO;
import cl.perfulandia.model.entity.Envio;
import cl.perfulandia.repository.EnvioRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/envios")
@Tag(name = "Envíos DB", description = "API para gestión de persistencia de envíos")
public class EnvioDbController {

    @Autowired
    private EnvioRepository repository;

    @GetMapping
    @Operation(summary = "Obtener todos los envíos", description = "Retorna una lista de todos los envíos registrados")
    @ApiResponse(responseCode = "200", description = "Lista de envíos obtenida exitosamente")
    public ResponseEntity<List<EnvioDTO>> findAll() {
        List<EnvioDTO> envios = repository.findAll()
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(envios);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener envío por ID", description = "Retorna un envío específico por su identificador")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Envío encontrado", 
                    content = @Content(schema = @Schema(implementation = EnvioDTO.class))),
        @ApiResponse(responseCode = "404", description = "Envío no encontrado")
    })
    public ResponseEntity<EnvioDTO> findById(@Parameter(description = "ID del envío") @PathVariable Long id) {
        return repository.findById(id)
                .map(e -> ResponseEntity.ok(toDTO(e)))
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/pedido/{pedidoId}")
    @Operation(summary = "Obtener envíos por pedido", description = "Retorna todos los envíos asociados a un pedido")
    @ApiResponse(responseCode = "200", description = "Envíos del pedido obtenidos exitosamente")
    public ResponseEntity<List<EnvioDTO>> findByPedidoId(@Parameter(description = "ID del pedido") @PathVariable Long pedidoId) {
        List<EnvioDTO> envios = repository.findByPedidoId(pedidoId)
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(envios);
    }

    @GetMapping("/estado/{estado}")
    @Operation(summary = "Obtener envíos por estado", description = "Retorna todos los envíos con un estado específico")
    @ApiResponse(responseCode = "200", description = "Envíos por estado obtenidos exitosamente")
    public ResponseEntity<List<EnvioDTO>> findByEstado(@Parameter(description = "Estado del envío") @PathVariable String estado) {
        List<EnvioDTO> envios = repository.findByEstado(estado)
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(envios);
    }

    @PostMapping
    @Operation(summary = "Crear nuevo envío", description = "Crea un nuevo envío en el sistema")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Envío creado exitosamente"),
        @ApiResponse(responseCode = "400", description = "Datos inválidos")
    })
    public ResponseEntity<EnvioDTO> save(@RequestBody EnvioDTO dto) {
        Envio entity = toEntity(dto);
        Envio saved = repository.save(entity);
        return ResponseEntity.status(HttpStatus.CREATED).body(toDTO(saved));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar envío", description = "Actualiza un envío existente")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Envío actualizado exitosamente"),
        @ApiResponse(responseCode = "404", description = "Envío no encontrado")
    })
    public ResponseEntity<EnvioDTO> update(@Parameter(description = "ID del envío") @PathVariable Long id, @RequestBody EnvioDTO dto) {
        if (!repository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        dto.setId(id);
        Envio actualizado = repository.save(toEntity(dto));
        return ResponseEntity.ok(toDTO(actualizado));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar envío", description = "Elimina un envío del sistema")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Envío eliminado exitosamente"),
        @ApiResponse(responseCode = "404", description = "Envío no encontrado")
    })
    public ResponseEntity<Void> delete(@Parameter(description = "ID del envío") @PathVariable Long id) {
        if (!repository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        repository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    private EnvioDTO toDTO(Envio e) {
        return new EnvioDTO(e.getId(), e.getPedidoId(), e.getDireccionDestino(), 
                           e.getTransportistaId(), e.getEstado(), e.getFechaEnvio(), e.getFechaEntrega());
    }

    private Envio toEntity(EnvioDTO dto) {
        return new Envio(dto.getId(), dto.getPedidoId(), dto.getDireccionDestino(),
                        dto.getTransportistaId(), dto.getEstado(), dto.getFechaEnvio(), dto.getFechaEntrega());
    }
}
