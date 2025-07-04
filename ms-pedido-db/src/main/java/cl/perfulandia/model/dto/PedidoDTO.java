package cl.perfulandia.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Objeto de transferencia de datos para pedidos")
public class PedidoDTO {
    @Schema(description = "Identificador único del pedido", example = "1")
    private Long id;
    
    @Schema(description = "Identificador del cliente", example = "100", required = true)
    private Long clienteId;
    
    @Schema(description = "Identificador del producto", example = "200", required = true)
    private Long productoId;
    
    @Schema(description = "Cantidad solicitada", example = "5", required = true)
    private Integer cantidad;
    
    @Schema(description = "Estado del pedido", example = "CREADO", allowableValues = {"CREADO", "PROCESADO", "CANCELADO"}, required = true)
    private String estado;
}
