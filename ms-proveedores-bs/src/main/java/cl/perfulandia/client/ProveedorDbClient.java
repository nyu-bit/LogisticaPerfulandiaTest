package cl.perfulandia.client;

import cl.perfulandia.model.dto.ProveedorDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "proveedores-db", url = "http://localhost:8087/proveedores")
public interface ProveedorDbClient {
    @GetMapping
    List<ProveedorDTO> findAll();

    @GetMapping("/{id}")
    ProveedorDTO findById(@PathVariable Long id);

    @GetMapping("/estado/{estado}")
    List<ProveedorDTO> findByEstado(@PathVariable String estado);

    @GetMapping("/categoria/{categoria}")
    List<ProveedorDTO> findByCategoria(@PathVariable String categoria);

    @GetMapping("/rut/{rut}")
    ProveedorDTO findByRut(@PathVariable String rut);

    @GetMapping("/buscar/{nombre}")
    List<ProveedorDTO> findByNombre(@PathVariable String nombre);

    @PostMapping
    ProveedorDTO save(@RequestBody ProveedorDTO proveedor);

    @PutMapping("/{id}")
    ProveedorDTO update(@PathVariable Long id, @RequestBody ProveedorDTO proveedor);

    @DeleteMapping("/{id}")
    void deleteById(@PathVariable Long id);
}
