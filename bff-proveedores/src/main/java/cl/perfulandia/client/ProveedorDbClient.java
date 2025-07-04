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

    @GetMapping("/categoria/{categoria}")
    List<ProveedorDTO> findByCategoria(@PathVariable String categoria);

    @PostMapping
    ProveedorDTO save(@RequestBody ProveedorDTO proveedor);

    @PutMapping("/{id}")
    ProveedorDTO update(@PathVariable Long id, @RequestBody ProveedorDTO proveedor);
}
