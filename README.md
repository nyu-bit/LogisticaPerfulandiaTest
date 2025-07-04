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

## 🚀 Ejecución Rápida

### 1. Compilar el proyecto
```bash
./gradlew build
```

### 2. Ejecutar pruebas unitarias
```bash
./gradlew test
```

### 3. Ejecutar microservicios individuales
En terminales separadas:

```bash
# Terminal 1 - Base de datos de pedidos
cd ms-pedido-db && ../gradlew bootRun

# Terminal 2 - Base de datos de envíos  
cd ms-envio-db && ../gradlew bootRun

# Terminal 3 - Base de datos de proveedores
cd ms-proveedores-db && ../gradlew bootRun

# Terminal 4 - Lógica de negocio de pedidos
cd ms-pedido-bs && ../gradlew bootRun

# Terminal 5 - Lógica de negocio de envíos
cd ms-envio-bs && ../gradlew bootRun

# Terminal 6 - Lógica de negocio de proveedores
cd ms-proveedores-bs && ../gradlew bootRun

# Terminal 7 - Backend for Frontend
cd bff-proveedores && ../gradlew bootRun
```

## 📚 Documentación Swagger

Una vez ejecutados los microservicios, la documentación interactiva estará disponible en:

- **BFF Principal**: http://localhost:8090/swagger-ui.html
- **Pedidos Business**: http://localhost:8084/swagger-ui.html
- **Envíos Business**: http://localhost:8086/swagger-ui.html
- **Proveedores Business**: http://localhost:8088/swagger-ui.html
- **Pedidos DB**: http://localhost:8083/swagger-ui.html
- **Envíos DB**: http://localhost:8085/swagger-ui.html
- **Proveedores DB**: http://localhost:8087/swagger-ui.html

## 🧪 Pruebas Unitarias

El proyecto incluye pruebas unitarias estratégicas:

### Ejecutar todas las pruebas
```bash
./gradlew test
```

### Ejecutar pruebas por microservicio
```bash
./gradlew ms-envio-bs:test
./gradlew ms-pedido-bs:test
./gradlew ms-proveedores-bs:test
./gradlew bff-proveedores:test
./gradlew ms-pedido-db:test
./gradlew ms-envio-db:test
./gradlew ms-proveedores-db:test
```

### Ver reportes de pruebas
Los reportes se generan en: `[microservicio]/build/reports/tests/test/index.html`

## 🏗️ Estructura del Proyecto

```
LogisticaPerfulandia/
├── bff-proveedores/           # Backend for Frontend
├── ms-pedido-bs/             # Microservicio de negocio - Pedidos  
├── ms-envio-bs/              # Microservicio de negocio - Envíos
├── ms-proveedores-bs/        # Microservicio de negocio - Proveedores
├── ms-pedido-db/             # Microservicio de datos - Pedidos
├── ms-envio-db/              # Microservicio de datos - Envíos
├── ms-proveedores-db/        # Microservicio de datos - Proveedores
├── build.gradle              # Configuración raíz de Gradle
├── settings.gradle           # Configuración de módulos
└── README.md                 # Documentación del proyecto
```

## ✅ Verificación de funcionamiento

1. **Compilación**: `./gradlew build` debe completarse sin errores
2. **Pruebas**: `./gradlew test` debe ejecutar todas las pruebas unitarias
3. **Swagger**: Acceder a http://localhost:8090/swagger-ui.html después de ejecutar los servicios

## 📝 Notas

- Los microservicios usan H2 Database en memoria, no requiere configuración adicional
- El proyecto está configurado para Java 17
- Todas las dependencias se descargan automáticamente con Gradle
