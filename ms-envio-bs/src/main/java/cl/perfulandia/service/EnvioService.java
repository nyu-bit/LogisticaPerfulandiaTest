package cl.perfulandia.service;

import cl.perfulandia.client.EnvioDbClient;
import cl.perfulandia.model.dto.EnvioDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class EnvioService {

    @Autowired
    private EnvioDbClient envioDbClient;

    public List<EnvioDTO> findAll() {
        return envioDbClient.findAll();
    }

    public EnvioDTO findById(Long id) {
        return envioDbClient.findById(id);
    }

    public List<EnvioDTO> findByPedidoId(Long pedidoId) {
        return envioDbClient.findByPedidoId(pedidoId);
    }

    public List<EnvioDTO> findByEstado(String estado) {
        return envioDbClient.findByEstado(estado);
    }

    public EnvioDTO save(EnvioDTO envio) {
        if (envio.getDireccionDestino() == null || envio.getDireccionDestino().trim().isEmpty()) {
            throw new IllegalArgumentException("La dirección de destino es obligatoria");
        }
        envio.setEstado("PENDIENTE");
        envio.setFechaEnvio(LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
        return envioDbClient.save(envio);
    }

    public EnvioDTO crearEnvioAutomatico(Long pedidoId, Long clienteId) {
        EnvioDTO nuevoEnvio = new EnvioDTO();
        nuevoEnvio.setPedidoId(pedidoId);
        nuevoEnvio.setDireccionDestino("Dirección pendiente de configurar");
        nuevoEnvio.setEstado("PENDIENTE");
        nuevoEnvio.setFechaEnvio(LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
        return envioDbClient.save(nuevoEnvio);
    }

    public EnvioDTO asignarTransportista(Long envioId, Long transportistaId) {
        EnvioDTO envio = findById(envioId);
        if (envio == null) {
            throw new IllegalArgumentException("Envío no encontrado");
        }
        if (!"PENDIENTE".equals(envio.getEstado())) {
            throw new IllegalStateException("Solo se puede asignar transportista a envíos pendientes");
        }
        envio.setTransportistaId(transportistaId);
        envio.setEstado("EN_TRANSITO");
        return envioDbClient.update(envioId, envio);
    }

    public EnvioDTO marcarComoEntregado(Long envioId) {
        EnvioDTO envio = findById(envioId);
        if (envio == null) {
            throw new IllegalArgumentException("Envío no encontrado");
        }
        if (!"EN_TRANSITO".equals(envio.getEstado())) {
            throw new IllegalStateException("Solo se pueden entregar envíos en tránsito");
        }
        envio.setEstado("ENTREGADO");
        envio.setFechaEntrega(LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
        return envioDbClient.update(envioId, envio);
    }

    public EnvioDTO update(Long id, EnvioDTO envio) {
        EnvioDTO existente = findById(id);
        if (existente == null) {
            throw new IllegalArgumentException("Envío no encontrado");
        }
        envio.setId(id);
        return envioDbClient.update(id, envio);
    }

    public void deleteById(Long id) {
        envioDbClient.deleteById(id);
    }
}
