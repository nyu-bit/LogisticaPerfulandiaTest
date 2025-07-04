package cl.perfulandia.client;

import cl.perfulandia.model.dto.InventarioDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "inventario-service", url = "http://localhost:8082/inventario")
public interface InventarioClient {
    @GetMapping("/stock-bajo")
    List<InventarioDTO> obtenerStockBajo();
}
