package cl.perfulandia.controller;

import cl.perfulandia.model.dto.*;
import cl.perfulandia.service.ProveedorService;
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
@RequestMapping("/proveedores-business")
@Tag(name = "Proveedores Business", description = "API para la lógica de gestión de proveedores")
public class ProveedorBsController {

    @Autowired
    private ProveedorService proveedorService;

    @GetMapping
    @Operation(summary = "Listar todos los proveedores")
    public List<ProveedorDTO> findAll() {
        return proveedorService.findAll();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar proveedor por ID")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Proveedor encontrado"),
        @ApiResponse(responseCode = "404", description = "Proveedor no encontrado")
    })
    public ResponseEntity<ProveedorDTO> findById(
            @Parameter(description = "ID del proveedor") @PathVariable Long id) {
        try {
            ProveedorDTO proveedor = proveedorService.findById(id);
            return proveedor != null ? ResponseEntity.ok(proveedor) : ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/activos")
    public List<ProveedorDTO> findActivos() {
        return proveedorService.findByEstado("ACTIVO");
    }

    @GetMapping("/categoria/{categoria}")
    public List<ProveedorDTO> findByCategoria(@PathVariable String categoria) {
        return proveedorService.findByCategoria(categoria);
    }

    @GetMapping("/buscar/{nombre}")
    public List<ProveedorDTO> findByNombre(@PathVariable String nombre) {
        return proveedorService.findByNombre(nombre);
    }

    @PostMapping
    public ResponseEntity<ProveedorDTO> save(@RequestBody ProveedorDTO proveedor) {
        try {
            ProveedorDTO nuevo = proveedorService.save(proveedor);
            return ResponseEntity.ok(nuevo);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProveedorDTO> update(@PathVariable Long id, @RequestBody ProveedorDTO proveedor) {
        try {
            ProveedorDTO actualizado = proveedorService.update(id, proveedor);
            return ResponseEntity.ok(actualizado);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/{id}/estado")
    public ResponseEntity<ProveedorDTO> actualizarEstado(@PathVariable Long id,
                                                         @RequestBody ActualizarEstadoRequest request) {
        try {
            ProveedorDTO actualizado = proveedorService.actualizarEstado(id, request.getEstado());
            return ResponseEntity.ok(actualizado);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/reposicion/verificar")
    public ResponseEntity<List<InventarioDTO>> verificarReposicion() {
        try {
            List<InventarioDTO> productos = proveedorService.verificarProductosParaReposicion();
            return ResponseEntity.ok(productos);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/reposicion/contactar")
    public ResponseEntity<Void> contactarParaReposicion(@RequestBody ContactarReposicionRequest request) {
        try {
            proveedorService.contactarProveedorParaReposicion(
                    request.getProveedorId(),
                    request.getProductoId(),
                    request.getCantidadSolicitada()
            );
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        try {
            proveedorService.deleteById(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
}

@lombok.Data
@lombok.NoArgsConstructor
@lombok.AllArgsConstructor
class ActualizarEstadoRequest {
    private String estado;
}

@lombok.Data
@lombok.NoArgsConstructor
@lombok.AllArgsConstructor
class ContactarReposicionRequest {
    private Long proveedorId;
    private Long productoId;
    private Integer cantidadSolicitada;
}
