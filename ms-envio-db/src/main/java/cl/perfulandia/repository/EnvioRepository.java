package cl.perfulandia.repository;

import cl.perfulandia.model.entity.Envio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface EnvioRepository extends JpaRepository<Envio, Long> {
    List<Envio> findByPedidoId(Long pedidoId);
    
    @Query("SELECT e FROM Envio e WHERE e.estado = ?1")
    List<Envio> findByEstado(String estado);
}
