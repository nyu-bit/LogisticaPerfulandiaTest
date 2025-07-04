package cl.perfulandia.client;

import cl.perfulandia.model.dto.PedidoDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "pedido-db", url = "${pedido.db.url}/pedidos")
public interface PedidoDbClient {
    @GetMapping
    List<PedidoDTO> findAll();

    @GetMapping("/{id}")
    PedidoDTO findById(@PathVariable Long id);

    @PostMapping
    PedidoDTO save(@RequestBody PedidoDTO pedido);

    @PutMapping("/{id}")
    PedidoDTO update(@PathVariable Long id, @RequestBody PedidoDTO pedido);

    @DeleteMapping("/{id}")
    void deleteById(@PathVariable Long id);
}
