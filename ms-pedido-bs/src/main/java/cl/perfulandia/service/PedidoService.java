package cl.perfulandia.service;

import cl.perfulandia.client.*;
import cl.perfulandia.model.dto.*;
import feign.FeignException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PedidoService {

    @Autowired
    private PedidoDbClient pedidoDbClient;

    @Autowired
    private UsuarioClient usuarioClient;

    @Autowired
    private OrdenClient ordenClient;

    @Autowired
    private EnvioClient envioClient;

    public List<PedidoDTO> findAll() {
        return pedidoDbClient.findAll();
    }

    public PedidoDTO findById(Long id) {
        return pedidoDbClient.findById(id);
    }

    public PedidoDTO crearPedido(Long clienteId, Long productoId, Integer cantidad) {
        // Validaciones de negocio
        if (cantidad <= 0) {
            throw new IllegalArgumentException("La cantidad debe ser mayor a 0");
        }

        try {
            // 1. Validar existencia del cliente (usuario)
            UsuarioDTO usuario = usuarioClient.obtener(clienteId);
            if (usuario == null) {
                throw new IllegalArgumentException("Cliente no encontrado");
            }

            // 2. Validar existencia del producto (orden)
            OrdenDTO orden = ordenClient.obtenerProducto(productoId);
            if (orden == null) {
                throw new IllegalArgumentException("Producto no encontrado");
            }

            // 3. Crear el pedido
            PedidoDTO nuevoPedido = new PedidoDTO();
            nuevoPedido.setClienteId(clienteId);
            nuevoPedido.setProductoId(productoId);
            nuevoPedido.setCantidad(cantidad);
            nuevoPedido.setEstado("CREADO");

            return pedidoDbClient.save(nuevoPedido);

        } catch (FeignException e) {
            throw new RuntimeException("Error en servicios externos: " + e.getMessage());
        }
    }

    public PedidoDTO procesarPedido(Long pedidoId, String accion) {
        PedidoDTO pedido = findById(pedidoId);
        if (pedido == null) {
            throw new IllegalArgumentException("Pedido no encontrado");
        }

        String estadoAnterior = pedido.getEstado();

        if ("PROCESAR".equals(accion)) {
            if (!"CREADO".equals(pedido.getEstado())) {
                throw new IllegalStateException("Solo se pueden procesar pedidos en estado CREADO");
            }
            pedido.setEstado("PROCESADO");
        } else if ("CANCELAR".equals(accion)) {
            if ("CANCELADO".equals(pedido.getEstado())) {
                throw new IllegalStateException("El pedido ya está cancelado");
            }
            pedido.setEstado("CANCELADO");
        } else {
            throw new IllegalArgumentException("Acción no válida");
        }

        PedidoDTO pedidoActualizado = pedidoDbClient.update(pedidoId, pedido);

        // Si el pedido cambió a PROCESADO, notificar al servicio de envío
        if ("PROCESADO".equals(pedidoActualizado.getEstado()) && 
            !"PROCESADO".equals(estadoAnterior)) {
            try {
                EnvioClient.PedidoProcesadoNotification notification = 
                    new EnvioClient.PedidoProcesadoNotification(
                        pedidoActualizado.getId(), 
                        pedidoActualizado.getClienteId(), 
                        pedidoActualizado.getProductoId()
                    );
                envioClient.notificarPedidoProcesado(notification);
            } catch (Exception e) {
                // Log error pero no fallar el proceso principal
                System.err.println("Error notificando a servicio de envío: " + e.getMessage());
            }
        }

        return pedidoActualizado;
    }

    public PedidoDTO cancelarPedido(Long id) {
        return procesarPedido(id, "CANCELAR");
    }

    public void deleteById(Long id) {
        pedidoDbClient.deleteById(id);
    }
}
