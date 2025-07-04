# LogísticaPerfulandia

Sistema de microservicios para la gestión de logística que incluye manejo de pedidos, envíos y proveedores. El proyecto implementa una arquitectura de microservicios con Spring Boot y documentación API mediante Swagger.

## Requisitos

- **Java**: 17+
- **Gradle**: 7.x o superior

## Arquitectura

El sistema está compuesto por 6 microservicios organizados en capas:

### Capa de Presentación
- **bff-proveedores** (puerto 8090): Backend for Frontend para operaciones de proveedores

### Capa de Negocio 
- **ms-pedido-bs** (puerto 8084): Lógica de negocio para gestión de pedidos
- **ms-envio-bs** (puerto 8086): Lógica de negocio para gestión de envíos  
- **ms-proveedores-bs** (puerto 8088): Lógica de negocio para gestión de proveedores

### Capa de Datos
- **ms-pedido-db** (puerto 8083): Acceso a datos para pedidos
- **ms-envio-db** (puerto 8085): Acceso a datos para envíos
- **ms-proveedores-db** (puerto 8087): Acceso a datos para proveedores

## Tecnologías

- **Framework**: Spring Boot 3.2.0
- **Build**: Gradle 7.x
- **Documentación API**: SpringDoc OpenAPI (Swagger)
- **Base de Datos**: H2 Database (en memoria)
- **Java**: 17+


Una vez ejecutados TODOS los microservicios, la documentación interactiva estará disponible en:

- **BFF Principal**: http://localhost:8090/swagger-ui.html
- **Pedidos Business**: http://localhost:8084/swagger-ui.html
- **Envíos Business**: http://localhost:8086/swagger-ui.html
- **Proveedores Business**: http://localhost:8088/swagger-ui.html
- **Pedidos DB**: http://localhost:8083/swagger-ui.html
- **Envíos DB**: http://localhost:8085/swagger-ui.html
- **Proveedores DB**: http://localhost:8087/swagger-ui.html



##  Verificación de funcionamiento

1. **Compilación**: `./gradlew build` debe completarse sin errores
2. **Pruebas**: `./gradlew test` debe ejecutar todas las pruebas unitarias
3. **Swagger**: Acceder a http://localhost:8090/swagger-ui.html después de ejecutar los servicios

 Notas

- Los microservicios usan H2 Database en memoria, no requiere configuración adicional
- El proyecto está configurado para Java 17
- Todas las dependencias se descargan automáticamente con Gradle
