package cl.perfulandia.model.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "envio")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Envio {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "pedido_id", nullable = false)
    private Long pedidoId;

    @Column(name = "direccion_destino", nullable = false)
    private String direccionDestino;

    @Column(name = "transportista_id")
    private Long transportistaId;

    @Column(nullable = false)
    private String estado; // PENDIENTE, EN_TRANSITO, ENTREGADO, DEVUELTO

    @Column(name = "fecha_envio")
    private String fechaEnvio;

    @Column(name = "fecha_entrega")
    private String fechaEntrega;
}
