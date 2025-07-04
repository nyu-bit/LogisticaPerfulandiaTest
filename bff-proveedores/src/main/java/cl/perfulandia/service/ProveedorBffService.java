package cl.perfulandia.service;

import cl.perfulandia.client.ProveedorDbClient;
import cl.perfulandia.client.ProveedorBsClient;
import cl.perfulandia.model.dto.ProveedorDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProveedorBffService {

    @Autowired
    private ProveedorDbClient proveedorDbClient;

    @Autowired
    private ProveedorBsClient proveedorBsClient;

    // Métodos simples para trabajadores
    public List<ProveedorDTO> obtenerProveedoresActivos() {
        return proveedorBsClient.obtenerProveedoresActivos();
    }

    public ProveedorDTO obtenerProveedorPorId(Long id) {
        return proveedorDbClient.findById(id);
    }

    public List<ProveedorDTO> buscarProveedoresPorCategoria(String categoria) {
        return proveedorDbClient.findByCategoria(categoria);
    }

    public ProveedorDTO crearProveedor(ProveedorDTO proveedor) {
        return proveedorDbClient.save(proveedor);
    }

    public List<ProveedorDTO> obtenerTodosProveedores() {
        return proveedorDbClient.findAll();
    }
}
