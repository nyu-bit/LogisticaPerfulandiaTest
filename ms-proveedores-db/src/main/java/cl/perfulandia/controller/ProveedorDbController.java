package cl.perfulandia.controller;

import cl.perfulandia.model.dto.ProveedorDTO;
import cl.perfulandia.model.entity.Proveedor;
import cl.perfulandia.repository.ProveedorRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/proveedores")
@Tag(name = "Proveedores DB", description = "API de gestión de datos de proveedores")
public class ProveedorDbController {

    @Autowired
    private ProveedorRepository repository;

    @GetMapping
    @Operation(summary = "Obtener todos los proveedores", description = "Retorna una lista completa de todos los proveedores registrados")
    @ApiResponse(responseCode = "200", description = "Lista de proveedores obtenida exitosamente")
    public ResponseEntity<List<ProveedorDTO>> findAll() {
        List<ProveedorDTO> proveedores = repository.findAll()
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(proveedores);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener proveedor por ID", description = "Retorna un proveedor específico basado en su ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Proveedor encontrado"),
        @ApiResponse(responseCode = "404", description = "Proveedor no encontrado")
    })
    public ResponseEntity<ProveedorDTO> findById(@Parameter(description = "ID del proveedor") @PathVariable Long id) {
        return repository.findById(id)
                .map(p -> ResponseEntity.ok(toDTO(p)))
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/estado/{estado}")
    @Operation(summary = "Obtener proveedores por estado", description = "Retorna todos los proveedores filtrados por estado")
    @ApiResponse(responseCode = "200", description = "Lista de proveedores por estado obtenida exitosamente")
    public ResponseEntity<List<ProveedorDTO>> findByEstado(@Parameter(description = "Estado del proveedor") @PathVariable String estado) {
        List<ProveedorDTO> proveedores = repository.findByEstado(estado)
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(proveedores);
    }

    @GetMapping("/categoria/{categoria}")
    @Operation(summary = "Obtener proveedores por categoría", description = "Retorna todos los proveedores filtrados por categoría")
    @ApiResponse(responseCode = "200", description = "Lista de proveedores por categoría obtenida exitosamente")
    public ResponseEntity<List<ProveedorDTO>> findByCategoria(@Parameter(description = "Categoría del proveedor") @PathVariable String categoria) {
        List<ProveedorDTO> proveedores = repository.findByCategoria(categoria)
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(proveedores);
    }

    @GetMapping("/rut/{rut}")
    @Operation(summary = "Obtener proveedor por RUT", description = "Retorna un proveedor específico basado en su RUT")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Proveedor encontrado"),
        @ApiResponse(responseCode = "404", description = "Proveedor no encontrado")
    })
    public ResponseEntity<ProveedorDTO> findByRut(@Parameter(description = "RUT del proveedor") @PathVariable String rut) {
        return repository.findByRut(rut)
                .map(p -> ResponseEntity.ok(toDTO(p)))
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/buscar/{nombre}")
    @Operation(summary = "Buscar proveedores por nombre", description = "Retorna proveedores que contengan el nombre especificado")
    @ApiResponse(responseCode = "200", description = "Lista de proveedores que coinciden con la búsqueda")
    public ResponseEntity<List<ProveedorDTO>> findByNombre(@Parameter(description = "Nombre o parte del nombre del proveedor") @PathVariable String nombre) {
        List<ProveedorDTO> proveedores = repository.findByNombreContaining(nombre)
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(proveedores);
    }

    @PostMapping
    @Operation(summary = "Crear nuevo proveedor", description = "Crea un nuevo proveedor en el sistema")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Proveedor creado exitosamente"),
        @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos")
    })
    public ResponseEntity<ProveedorDTO> save(@Parameter(description = "Datos del proveedor a crear") @RequestBody ProveedorDTO dto) {
        Proveedor entity = toEntity(dto);
        Proveedor saved = repository.save(entity);
        return ResponseEntity.status(HttpStatus.CREATED).body(toDTO(saved));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar proveedor", description = "Actualiza un proveedor existente")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Proveedor actualizado exitosamente"),
        @ApiResponse(responseCode = "404", description = "Proveedor no encontrado"),
        @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos")
    })
    public ResponseEntity<ProveedorDTO> update(@Parameter(description = "ID del proveedor") @PathVariable Long id, 
                                              @Parameter(description = "Datos actualizados del proveedor") @RequestBody ProveedorDTO dto) {
        if (!repository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        dto.setId(id);
        Proveedor actualizado = repository.save(toEntity(dto));
        return ResponseEntity.ok(toDTO(actualizado));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar proveedor", description = "Elimina un proveedor del sistema")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Proveedor eliminado exitosamente"),
        @ApiResponse(responseCode = "404", description = "Proveedor no encontrado")
    })
    public ResponseEntity<Void> delete(@Parameter(description = "ID del proveedor a eliminar") @PathVariable Long id) {
        if (!repository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        repository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    private ProveedorDTO toDTO(Proveedor p) {
        return new ProveedorDTO(p.getId(), p.getNombre(), p.getRut(), p.getDireccion(),
                               p.getTelefono(), p.getEmail(), p.getEstado(), p.getCategoria());
    }

    private Proveedor toEntity(ProveedorDTO dto) {
        return new Proveedor(dto.getId(), dto.getNombre(), dto.getRut(), dto.getDireccion(),
                            dto.getTelefono(), dto.getEmail(), dto.getEstado(), dto.getCategoria());
    }
}
