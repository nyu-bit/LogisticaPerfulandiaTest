package cl.perfulandia.client;

import cl.perfulandia.model.dto.OrdenDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "orden-bff", url = "${orden.service.url}/api/ordenes")
public interface OrdenClient {
    @GetMapping("/producto/{id}")
    OrdenDTO obtenerProducto(@PathVariable("id") Long id);
}
