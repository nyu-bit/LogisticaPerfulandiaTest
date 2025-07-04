package cl.perfulandia.controller;

import cl.perfulandia.model.dto.ProveedorDTO;
import cl.perfulandia.service.ProveedorBffService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/proveedores")
@Tag(name = "Proveedores BFF", description = "API para gestión de proveedores a través del Backend for Frontend")
public class ProveedorBffController {

    @Autowired
    private ProveedorBffService service;

    @GetMapping
    @Operation(summary = "Obtener todos los proveedores", description = "Retorna una lista de todos los proveedores registrados")
    @ApiResponse(responseCode = "200", description = "Lista de proveedores obtenida exitosamente")
    public List<ProveedorDTO> obtenerTodos() {
        return service.obtenerTodosProveedores();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener proveedor por ID", description = "Retorna un proveedor específico basado en su ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Proveedor encontrado"),
        @ApiResponse(responseCode = "404", description = "Proveedor no encontrado")
    })
    public ResponseEntity<ProveedorDTO> obtenerPorId(
            @Parameter(description = "ID del proveedor a buscar") @PathVariable Long id) {
        try {
            ProveedorDTO proveedor = service.obtenerProveedorPorId(id);
            return proveedor != null ? ResponseEntity.ok(proveedor) : ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/activos")
    @Operation(summary = "Obtener proveedores activos", description = "Retorna una lista de todos los proveedores que están activos")
    @ApiResponse(responseCode = "200", description = "Lista de proveedores activos obtenida exitosamente")
    public List<ProveedorDTO> obtenerActivos() {
        return service.obtenerProveedoresActivos();
    }

    @GetMapping("/categoria/{categoria}")
    @Operation(summary = "Obtener proveedores por categoría", description = "Retorna una lista de proveedores filtrados por categoría")
    @ApiResponse(responseCode = "200", description = "Lista de proveedores por categoría obtenida exitosamente")
    public List<ProveedorDTO> obtenerPorCategoria(
            @Parameter(description = "Categoría de los proveedores a buscar") @PathVariable String categoria) {
        return service.buscarProveedoresPorCategoria(categoria);
    }

    @PostMapping
    @Operation(summary = "Crear nuevo proveedor", description = "Crea un nuevo proveedor en el sistema")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Proveedor creado exitosamente"),
        @ApiResponse(responseCode = "400", description = "Error en los datos del proveedor")
    })
    public ResponseEntity<ProveedorDTO> crearProveedor(
            @Parameter(description = "Datos del proveedor a crear") @RequestBody ProveedorDTO proveedor) {
        try {
            ProveedorDTO nuevo = service.crearProveedor(proveedor);
            return ResponseEntity.ok(nuevo);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
