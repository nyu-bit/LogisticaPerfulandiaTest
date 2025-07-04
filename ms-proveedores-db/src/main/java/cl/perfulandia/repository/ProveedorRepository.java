package cl.perfulandia.repository;

import cl.perfulandia.model.entity.Proveedor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ProveedorRepository extends JpaRepository<Proveedor, Long> {
    List<Proveedor> findByEstado(String estado);
    
    List<Proveedor> findByCategoria(String categoria);
    
    Optional<Proveedor> findByRut(String rut);
    
    @Query("SELECT p FROM Proveedor p WHERE p.nombre LIKE %?1%")
    List<Proveedor> findByNombreContaining(String nombre);
}
