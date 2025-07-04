package cl.perfulandia.model.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EnvioDTO {
    private Long id;
    private Long pedidoId;
    private String direccionDestino;
    private Long transportistaId;
    private String estado;
    private String fechaEnvio;
    private String fechaEntrega;
}
