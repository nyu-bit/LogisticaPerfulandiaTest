package cl.perfulandia.model.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PedidoDTO {
    private Long id;
    private Long clienteId;
    private Long productoId;
    private Integer cantidad;
    private String estado;
}

@Data
@NoArgsConstructor
@AllArgsConstructor
class CrearPedidoRequest {
    private Long clienteId;
    private Long productoId;
    private Integer cantidad;
}

@Data
@NoArgsConstructor
@AllArgsConstructor
class ProcesarPedidoRequest {
    private Long pedidoId;
    private String accion; // PROCESAR, CANCELAR
}
