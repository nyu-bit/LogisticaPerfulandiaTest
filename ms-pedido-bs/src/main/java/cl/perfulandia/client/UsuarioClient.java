package cl.perfulandia.client;

import cl.perfulandia.model.dto.UsuarioDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "usuario-bff", url = "${usuario.service.url}/api/usuarios")
public interface UsuarioClient {
    @GetMapping("/{id}")
    UsuarioDTO obtener(@PathVariable("id") Long id);
}
