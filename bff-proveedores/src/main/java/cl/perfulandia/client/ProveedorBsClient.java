package cl.perfulandia.client;

import cl.perfulandia.model.dto.ProveedorDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "proveedores-bs", url = "http://localhost:8088/proveedores-business")
public interface ProveedorBsClient {
    @GetMapping("/activos")
    List<ProveedorDTO> obtenerProveedoresActivos();

    @GetMapping("/categoria/{categoria}")
    List<ProveedorDTO> obtenerProveedoresPorCategoria(@PathVariable String categoria);
}
