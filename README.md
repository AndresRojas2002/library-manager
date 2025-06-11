# Sistema de Gestión de Biblioteca 📚

Este es un sistema de gestión de biblioteca desarrollado en Java con Spring Boot que permite administrar libros, usuarios, préstamos y devoluciones de manera eficiente.

## Autor 👨‍💻

**Luis Andrés Rojas Acevedo**
- GitHub: [@AndresRojas2002](https://github.com/AndresRojas2002)

## Características Principales 🌟

- Gestión completa de libros (CRUD)
- Gestión de usuarios
- Sistema de préstamos y devoluciones
- Búsqueda avanzada por diferentes criterios
- Control de estado de libros (disponible/prestado)
- Control de estado de usuarios (con/sin préstamo)
- API RESTful con documentación OpenAPI/Swagger
- Validación de datos
- Manejo de excepciones personalizado

## Tecnologías Utilizadas 🛠️

- Java 17
- Spring Boot 3.x
- Spring Data JPA
- PostgreSQL
- Lombok
- Maven
- OpenAPI 3.0 (Swagger)
- Jakarta Validation

## Estructura del Proyecto 📁

```
src/main/java/co/com/andres/
├── controllers/     # Controladores REST con documentación OpenAPI
├── services/       # Lógica de negocio
├── repositories/   # Acceso a datos
├── models/         # Entidades y DTOs
└── exceptions/     # Manejo de excepciones
```

## Documentación de la API 🌐

La API está documentada usando OpenAPI 3.0. Puedes acceder a la documentación interactiva en:
```
http://localhost:8080/swagger-ui.html
```

### Endpoints de la API

#### Gestión de Libros

| Método | Endpoint | Descripción | Respuestas |
|--------|----------|-------------|------------|
| POST | `/api/libros` | Crear un nuevo libro | 201: Creado, 400: Datos inválidos |
| GET | `/api/libros` | Obtener todos los libros | 200: OK |
| GET | `/api/libros/{id}` | Obtener libro por ID | 200: OK, 404: No encontrado |
| PUT | `/api/libros/{id}` | Actualizar libro | 200: OK, 404: No encontrado, 400: Datos inválidos |
| DELETE | `/api/libros/{id}` | Eliminar libro | 200: OK, 404: No encontrado |
| GET | `/api/libros/textoBusqueda?q={texto}` | Buscar por autor o título | 200: OK |
| GET | `/api/libros/genero?g={genero}` | Buscar por género | 200: OK |
| GET | `/api/libros/disponibles` | Listar libros disponibles | 200: OK |
| GET | `/api/libros/prestados` | Listar libros prestados | 200: OK |

#### Gestión de Usuarios

| Método | Endpoint | Descripción | Respuestas |
|--------|----------|-------------|------------|
| POST | `/api/usuarios` | Crear un nuevo usuario | 201: Creado, 400: Datos inválidos |
| GET | `/api/usuarios` | Obtener todos los usuarios | 200: OK |
| GET | `/api/usuarios/{id}` | Obtener usuario por ID | 200: OK, 404: No encontrado |
| PUT | `/api/usuarios/{id}` | Actualizar usuario | 200: OK, 404: No encontrado, 400: Datos inválidos |
| DELETE | `/api/usuarios/{id}` | Eliminar usuario | 200: OK, 404: No encontrado |
| GET | `/api/usuarios/con-prestamo` | Obtener usuarios con préstamo activo | 200: OK |
| GET | `/api/usuarios/sin-prestamo` | Obtener usuarios sin préstamo activo | 200: OK |

#### Gestión de Préstamos

| Método | Endpoint | Descripción | Respuestas |
|--------|----------|-------------|------------|
| GET | `/api/prestamos` | Obtener todos los préstamos | 200: OK |
| POST | `/api/prestamos/create` | Crear nuevo préstamo | 200: OK, 404: No encontrado, 400: No disponible |
| DELETE | `/api/prestamos/{id}` | Eliminar préstamo | 200: OK, 404: No encontrado |
| PATCH | `/api/prestamos/devolver/{id}` | Devolver libro | 200: OK, 404: No encontrado, 400: No activo |

## Modelo de Datos 📊

### Libro (Books)
- ID (autogenerado)
- Título (obligatorio)
- Autor (obligatorio)
- ISBN (único, obligatorio)
- Año de publicación (obligatorio)
- Género (obligatorio)
- Estado (AVAILABLE/LOANED)

### Usuario (Users)
- ID (autogenerado)
- Nombre (obligatorio)
- Apellido (obligatorio)
- Email (obligatorio)
- Teléfono
- Dirección (obligatorio)
- Fecha de registro (obligatorio)
- Estado (WITHOUT_LOAN/WITH_LOAN)

### Préstamo (Loans)
- ID (autogenerado)
- Usuario (relación ManyToOne)
- Libro (relación ManyToOne)
- Fecha de préstamo (obligatorio)
- Estado (ACTIVE/NOT_ACTIVE)

## Validaciones de Préstamos 🔒

- Un libro solo puede ser prestado si está disponible (AVAILABLE)
- Un usuario solo puede tener un préstamo activo a la vez
- Al prestar un libro:
  - El estado del libro cambia a LOANED
  - El estado del usuario cambia a WITH_LOAN
  - Se crea un préstamo con estado ACTIVE
- Al devolver un libro:
  - El estado del libro cambia a AVAILABLE
  - El estado del usuario cambia a WITHOUT_LOAN
  - El estado del préstamo cambia a NOT_ACTIVE

## Manejo de Excepciones 🔍

El sistema implementa un manejo global de excepciones que proporciona respuestas estandarizadas para diferentes tipos de errores:

### Tipos de Excepciones Manejadas

- **Conflictos de Datos**
  - ISBN duplicado
  - Correo electrónico duplicado
  - Libro ya prestado
  - Usuario con préstamo activo

- **Recursos No Encontrados**
  - Libro no encontrado
  - Usuario no encontrado
  - Préstamo no encontrado

- **Errores del Sistema**
  - Errores de tiempo de ejecución
  - Errores generales no manejados

### Formato de Respuesta de Error

```json
{
    "timestamp": "2024-03-21T10:30:00",
    "status": 404,
    "error": "Not Found",
    "message": "Libro no encontrado con ID: 123",
    "path": "/api/libros/123"
}
```

### Códigos de Estado HTTP

- 200: OK
- 201: Creado
- 400: Solicitud incorrecta
- 404: Recurso no encontrado
- 409: Conflicto
- 500: Error interno del servidor

## Instalación y Configuración ⚙️

1. Clonar el repositorio
```bash
git clone [url-del-repositorio]
```

2. Configurar la base de datos en `application.properties`
```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/Library_Manager
spring.datasource.username=postgres
spring.datasource.password=tu_contraseña
```

3. Ejecutar con Maven:
```bash
mvn spring-boot:run
```

4. Acceder a la documentación Swagger:
```
http://localhost:8080/swagger-ui.html
```




