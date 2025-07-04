package cl.perfulandia.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.Contact;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("BFF Proveedores API")
                        .description("Backend for Frontend para la gestión de proveedores en Logística Perfulandia")
                        .version("1.0.0")
                        .contact(new Contact()
                                .name("Equipo Logística Perfulandia")
                                .email("desarrollo@perfulandia.cl")));
    }
}
