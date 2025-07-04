package cl.perfulandia.model.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "proveedor")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Proveedor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nombre;

    @Column(nullable = false)
    private String rut;

    @Column
    private String direccion;

    @Column
    private String telefono;

    @Column
    private String email;

    @Column(nullable = false)
    private String estado; // ACTIVO, INACTIVO, SUSPENDIDO

    @Column(name = "categoria")
    private String categoria; // TRANSPORTE, ALMACENAMIENTO, EMPAQUE, etc.
}
