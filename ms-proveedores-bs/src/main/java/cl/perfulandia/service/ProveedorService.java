package cl.perfulandia.service;

import cl.perfulandia.client.*;
import cl.perfulandia.model.dto.*;
import feign.FeignException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProveedorService {

    @Autowired
    private ProveedorDbClient proveedorDbClient;

    @Autowired
    private InventarioClient inventarioClient;

    public List<ProveedorDTO> findAll() {
        return proveedorDbClient.findAll();
    }

    public ProveedorDTO findById(Long id) {
        return proveedorDbClient.findById(id);
    }

    public List<ProveedorDTO> findByEstado(String estado) {
        return proveedorDbClient.findByEstado(estado);
    }

    public List<ProveedorDTO> findByCategoria(String categoria) {
        return proveedorDbClient.findByCategoria(categoria);
    }

    public ProveedorDTO findByRut(String rut) {
        return proveedorDbClient.findByRut(rut);
    }

    public List<ProveedorDTO> findByNombre(String nombre) {
        return proveedorDbClient.findByNombre(nombre);
    }

    public ProveedorDTO save(ProveedorDTO proveedor) {
        // Validaciones de negocio
        if (proveedor.getNombre() == null || proveedor.getNombre().trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre es obligatorio");
        }
        if (proveedor.getRut() == null || proveedor.getRut().trim().isEmpty()) {
            throw new IllegalArgumentException("El RUT es obligatorio");
        }

        try {
            // Verificar si el RUT ya existe
            ProveedorDTO existente = proveedorDbClient.findByRut(proveedor.getRut());
            if (existente != null) {
                throw new IllegalArgumentException("El RUT ya está registrado");
            }
        } catch (FeignException.NotFound e) {
            // RUT no existe, puede continuar
        }

        proveedor.setEstado("ACTIVO");
        return proveedorDbClient.save(proveedor);
    }

    public ProveedorDTO update(Long id, ProveedorDTO proveedor) {
        ProveedorDTO existente = findById(id);
        if (existente == null) {
            throw new IllegalArgumentException("Proveedor no encontrado");
        }
        proveedor.setId(id);
        return proveedorDbClient.update(id, proveedor);
    }

    public ProveedorDTO actualizarEstado(Long id, String nuevoEstado) {
        ProveedorDTO proveedor = findById(id);
        if (proveedor == null) {
            throw new IllegalArgumentException("Proveedor no encontrado");
        }
        if (!esEstadoValido(nuevoEstado)) {
            throw new IllegalArgumentException("Estado no válido");
        }
        proveedor.setEstado(nuevoEstado);
        return proveedorDbClient.update(id, proveedor);
    }

    public List<InventarioDTO> verificarProductosParaReposicion() {
        try {
            return inventarioClient.obtenerStockBajo();
        } catch (FeignException e) {
            throw new RuntimeException("Error consultando inventario: " + e.getMessage());
        }
    }

    public void contactarProveedorParaReposicion(Long proveedorId, Long productoId, Integer cantidad) {
        ProveedorDTO proveedor = findById(proveedorId);
        if (proveedor == null) {
            throw new IllegalArgumentException("Proveedor no encontrado");
        }
        if (!"ACTIVO".equals(proveedor.getEstado())) {
            throw new IllegalStateException("El proveedor no está activo");
        }
        
        // lógica para contactar al proveedor
        // Por ejemplo su: enviar email, crear orden de compra, etc.
        System.out.println(String.format(
                "Contactando proveedor %s (ID: %d) para reposición de producto %d, cantidad: %d",
                proveedor.getNombre(), proveedorId, productoId, cantidad
        ));
    }

    public void deleteById(Long id) {
        proveedorDbClient.deleteById(id);
    }

    private boolean esEstadoValido(String estado) {
        return "ACTIVO".equals(estado) || "INACTIVO".equals(estado) || "SUSPENDIDO".equals(estado);
    }
}
