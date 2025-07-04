package cl.perfulandia.controller;

import cl.perfulandia.model.dto.PedidoDTO;
import cl.perfulandia.service.PedidoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pedidos-business")
@Tag(name = "Pedidos Business", description = "API de lógica de negocio para gestión de pedidos")
public class PedidoBsController {

    @Autowired
    private PedidoService pedidoService;

    @GetMapping
    @Operation(summary = "Obtener todos los pedidos", description = "Retorna una lista completa de todos los pedidos")
    @ApiResponse(responseCode = "200", description = "Lista de pedidos obtenida exitosamente")
    public List<PedidoDTO> obtenerTodos() {
        return pedidoService.findAll();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener pedido por ID", description = "Retorna un pedido específico basado en su ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Pedido encontrado"),
        @ApiResponse(responseCode = "404", description = "Pedido no encontrado")
    })
    public ResponseEntity<PedidoDTO> obtenerPorId(@Parameter(description = "ID del pedido") @PathVariable Long id) {
        try {
            PedidoDTO pedido = pedidoService.findById(id);
            return pedido != null ? ResponseEntity.ok(pedido) : ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/crear")
    @Operation(summary = "Crear nuevo pedido", description = "Crea un nuevo pedido con los datos proporcionados")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Pedido creado exitosamente"),
        @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos")
    })
    public ResponseEntity<PedidoDTO> crearPedido(@Parameter(description = "Datos para crear el pedido") @RequestBody CrearPedidoRequest request) {
        try {
            PedidoDTO pedido = pedidoService.crearPedido(
                    request.getClienteId(),
                    request.getProductoId(),
                    request.getCantidad()
            );
            return ResponseEntity.ok(pedido);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/procesar")
    @Operation(summary = "Procesar pedido", description = "Procesa un pedido existente (PROCESAR o CANCELAR)")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Pedido procesado exitosamente"),
        @ApiResponse(responseCode = "400", description = "Error al procesar el pedido")
    })
    public ResponseEntity<PedidoDTO> procesarPedido(@Parameter(description = "Datos para procesar el pedido") @RequestBody ProcesarPedidoRequest request) {
        try {
            PedidoDTO pedido = pedidoService.procesarPedido(request.getPedidoId(), request.getAccion());
            return ResponseEntity.ok(pedido);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar pedido", description = "Elimina un pedido del sistema")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Pedido eliminado exitosamente"),
        @ApiResponse(responseCode = "404", description = "Pedido no encontrado")
    })
    public ResponseEntity<Void> eliminarPedido(@Parameter(description = "ID del pedido a eliminar") @PathVariable Long id) {
        try {
            pedidoService.deleteById(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
}

@lombok.Data
@lombok.NoArgsConstructor
@lombok.AllArgsConstructor
class CrearPedidoRequest {
    private Long clienteId;
    private Long productoId;
    private Integer cantidad;
}

@lombok.Data
@lombok.NoArgsConstructor
@lombok.AllArgsConstructor
class ProcesarPedidoRequest {
    private Long pedidoId;
    private String accion; // PROCESAR, CANCELAR
}
