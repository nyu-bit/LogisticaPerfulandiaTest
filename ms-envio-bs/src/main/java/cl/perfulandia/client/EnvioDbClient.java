package cl.perfulandia.client;

import cl.perfulandia.model.dto.EnvioDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "envio-db", url = "http://localhost:8085/envios")
public interface EnvioDbClient {
    @GetMapping
    List<EnvioDTO> findAll();

    @GetMapping("/{id}")
    EnvioDTO findById(@PathVariable Long id);

    @GetMapping("/pedido/{pedidoId}")
    List<EnvioDTO> findByPedidoId(@PathVariable Long pedidoId);

    @GetMapping("/estado/{estado}")
    List<EnvioDTO> findByEstado(@PathVariable String estado);

    @PostMapping
    EnvioDTO save(@RequestBody EnvioDTO envio);

    @PutMapping("/{id}")
    EnvioDTO update(@PathVariable Long id, @RequestBody EnvioDTO envio);

    @DeleteMapping("/{id}")
    void deleteById(@PathVariable Long id);
}
