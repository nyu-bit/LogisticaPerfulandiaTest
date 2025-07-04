package cl.perfulandia.model.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InventarioDTO {
    private Long id;
    private String nombre;
    private String categoria;
    private Integer stockActual;
    private Integer stockMinimo;
}
