package cl.perfulandia.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "envio-bs", url = "${envio.bs.url}/envios-business")
public interface EnvioClient {
    @PostMapping("/procesar-pedido")
    void notificarPedidoProcesado(@RequestBody PedidoProcesadoNotification notification);
    
    public static record PedidoProcesadoNotification(Long pedidoId, Long clienteId, Long productoId) {}
}
