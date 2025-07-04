package cl.perfulandia.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Objeto de transferencia de datos para envíos")
public class EnvioDTO {
    @Schema(description = "Identificador único del envío", example = "1")
    private Long id;
    
    @Schema(description = "Identificador del pedido asociado", example = "100", required = true)
    private Long pedidoId;
    
    @Schema(description = "Dirección de destino del envío", example = "Av. Principal 123, Santiago", required = true)
    private String direccionDestino;
    
    @Schema(description = "Identificador del transportista", example = "50")
    private Long transportistaId;
    
    @Schema(description = "Estado del envío", example = "PENDIENTE", 
            allowableValues = {"PENDIENTE", "EN_TRANSITO", "ENTREGADO", "DEVUELTO"}, required = true)
    private String estado;
    
    @Schema(description = "Fecha de envío", example = "2025-07-03")
    private String fechaEnvio;
    
    @Schema(description = "Fecha de entrega", example = "2025-07-05")
    private String fechaEntrega;
}
