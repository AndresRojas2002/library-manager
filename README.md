# Sistema de Gestión de Biblioteca 📚

Este es un sistema de gestión de biblioteca desarrollado en Java con Spring Boot que permite administrar libros, préstamos y devoluciones de manera eficiente.

## Autor 👨‍💻

**Luis Andrés Rojas Acevedo**
- GitHub: [@AndresRojas2002](https://github.com/AndresRojas2002)

## Características Principales 🌟

- Gestión completa de libros (CRUD)
- Sistema de préstamos y devoluciones
- Búsqueda avanzada por diferentes criterios
- Control de estado de libros (disponible/prestado)
- API RESTful con documentación OpenAPI/Swagger
- Validación de datos
- Manejo de excepciones personalizado

## Tecnologías Utilizadas 🛠️

- Java 17
- Spring Boot 3.x
- Spring Data JPA
- MySQL/PostgreSQL (configurable)
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

#### Búsquedas

| Método | Endpoint | Descripción | Respuestas |
|--------|----------|-------------|------------|
| GET | `/api/libros/textoBusqueda?q={texto}` | Buscar por autor o título | 200: OK |
| GET | `/api/libros/genero?g={genero}` | Buscar por género | 200: OK |

#### Gestión de Préstamos

| Método | Endpoint | Descripción | Respuestas |
|--------|----------|-------------|------------|
| GET | `/api/libros/disponibles` | Listar libros disponibles | 200: OK |
| GET | `/api/libros/prestados` | Listar libros prestados | 200: OK |
| PATCH | `/api/libros/{id}/prestar` | Prestar un libro | 200: OK, 404: No encontrado, 400: No disponible |
| PATCH | `/api/libros/{id}/devolver` | Devolver un libro | 200: OK, 404: No encontrado, 400: No prestado |

## Modelo de Datos 📊

### Libro (Books)
- ID (autogenerado)
- Título (obligatorio)
- Autor (obligatorio)
- ISBN (único, obligatorio)
- Año de publicación (obligatorio)
- Género (obligatorio)
- Estado (AVAILABLE/LOANED)

## Características de Búsqueda 🔍

- Búsqueda por género ignorando mayúsculas/minúsculas
- Búsqueda por autor o título con coincidencia parcial
- Ignorancia de comillas en las búsquedas
- Filtrado por estado del libro

## Ejemplos de Uso 💡

### Crear un libro
```json
POST /api/libros
{
    "title": "El Quijote",
    "author": "Miguel de Cervantes",
    "isbn": "978-3-16-148410-0",
    "yearOfPublication": "1605-01-01",
    "gender": "Novela"
}
```

### Buscar por género
```
GET /api/libros/genero?g=ficción
```
La búsqueda es flexible y encontrará coincidencias incluso si:
- Se usan comillas ("ficción" o 'ficción')
- Se usan mayúsculas/minúsculas diferentes
- Se busca una parte del género

## Instalación y Configuración ⚙️

1. Clonar el repositorio
```bash
git clone [url-del-repositorio]
```

2. Configurar la base de datos en `application.properties`
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/library
spring.datasource.username=tu_usuario
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




