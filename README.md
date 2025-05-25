# Sistema de Gestión de Biblioteca 📚

Este es un sistema de gestión de biblioteca desarrollado en Java con Spring Boot que permite administrar libros, préstamos y devoluciones de manera eficiente.

## Características Principales 🌟

- Gestión completa de libros (CRUD)
- Sistema de préstamos y devoluciones
- Búsqueda avanzada por diferentes criterios
- Control de estado de libros (disponible/prestado)
- API RESTful para integración con otros sistemas

## Tecnologías Utilizadas 🛠️

- Java 17
- Spring Boot
- Spring Data JPA
- MySQL/PostgreSQL (configurable)
- Lombok
- Maven

## Estructura del Proyecto 📁

```
src/main/java/co/com/andres/
├── controllers/     # Controladores REST
├── services/       # Lógica de negocio
├── repositories/   # Acceso a datos
├── models/         # Entidades y DTOs
└── exceptions/     # Manejo de excepciones
```

## Endpoints de la API 🌐

### Libros

- `POST /api/libros` - Crear uno o varios libros
- `GET /api/libros` - Obtener todos los libros
- `GET /api/libros/{id}` - Obtener libro por ID
- `PUT /api/libros/{id}` - Actualizar libro
- `DELETE /api/libros/{id}` - Eliminar libro

### Búsquedas

- `GET /api/libros/textoBusqueda?q={texto}` - Buscar por autor o título
- `GET /api/libros/genero?gender={genero}` - Buscar por género

### Gestión de Préstamos

- `GET /api/libros/disponibles` - Listar libros disponibles
- `GET /api/libros/prestados` - Listar libros prestados
- `PATCH /api/libros/{id}/prestar` - Prestar un libro
- `PATCH /api/libros/{id}/devolver` - Devolver un libro

## Modelo de Datos 📊

### Libro (Books)
- ID (autogenerado)
- Título
- Autor
- ISBN (único)
- Año de publicación
- Género
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
GET /api/libros/genero?gender=ficción
```
La búsqueda es flexible y encontrará coincidencias incluso si:
- Se usan comillas ("ficción" o 'ficción')
- Se usan mayúsculas/minúsculas diferentes
- Se busca una parte del género

## Instalación y Configuración ⚙️

1. Clonar el repositorio
2. Configurar la base de datos en `application.properties`
3. Ejecutar con Maven:
```bash
mvn spring-boot:run
```

