package co.com.andres.controllers;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import co.com.andres.models.dto.BookRequest;
import co.com.andres.models.dto.BookResponse;
import co.com.andres.services.BookServices;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;

@Tag(name = "Libros", description = "API para gestionar los libros de la biblioteca")
@RestController
@RequestMapping("/api/libros")
@RequiredArgsConstructor
public class BookController {

    private final BookServices bookServices;

    @Operation(summary = "Crear un nuevo libro", description = "Crea un nuevo libro en la biblioteca")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Libro creado exitosamente",
            content = @Content(schema = @Schema(implementation = BookResponse.class))),
        @ApiResponse(responseCode = "400", description = "Datos del libro inválidos")
    })
    @PostMapping
    public BookResponse createBooks(@Valid @RequestBody BookRequest bookRequests) {
        return bookServices.createBook(bookRequests);
    }

    @Operation(summary = "Obtener todos los libros", description = "Retorna una lista de todos los libros disponibles en la biblioteca")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista de libros obtenida exitosamente",
            content = @Content(schema = @Schema(implementation = BookResponse.class)))
    })
    @GetMapping
    public List<BookResponse> getAllBooks() {
        return bookServices.getAll();
    }

    @Operation(summary = "Obtener libro por ID", description = "Retorna un libro específico basado en su ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Libro encontrado exitosamente"),
        @ApiResponse(responseCode = "404", description = "Libro no encontrado")
    })
    @GetMapping("{id}")
    public BookResponse getById(@Parameter(description = "ID del libro") @PathVariable("id") Long id) {
        return bookServices.getById(id);
    }

    @Operation(summary = "Actualizar libro", description = "Actualiza la información de un libro existente")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Libro actualizado exitosamente"),
        @ApiResponse(responseCode = "404", description = "Libro no encontrado"),
        @ApiResponse(responseCode = "400", description = "Datos del libro inválidos")
    })
    @PutMapping("{id}")
    public BookResponse updateBook(
        @Parameter(description = "ID del libro a actualizar") @Valid @Min(0) @PathVariable("id") long id,
        @Valid @RequestBody BookRequest bookRequest) {
        return bookServices.updateById(id, bookRequest);
    }

    @Operation(summary = "Eliminar libro", description = "Elimina un libro de la biblioteca")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Libro eliminado exitosamente"),
        @ApiResponse(responseCode = "404", description = "Libro no encontrado")
    })
    @DeleteMapping("{id}")
    public BookResponse deleteBook(@Parameter(description = "ID del libro a eliminar") @PathVariable("id") long id) {
        return bookServices.deleteById(id);
    }

    @Operation(summary = "Buscar libros por autor o título", description = "Busca libros que coincidan con el texto proporcionado en el autor o título")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Búsqueda realizada exitosamente")
    })
    @GetMapping("/textoBusqueda")
    public List<BookResponse> getByAuthorOrTitle(
        @Parameter(description = "Texto de búsqueda") @RequestParam("q") String text) {
        return bookServices.getByAuthorOrTitle(text);
    }

    @Operation(summary = "Obtener libros disponibles", description = "Retorna una lista de todos los libros que están disponibles para préstamo")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista de libros disponibles obtenida exitosamente")
    })
    @GetMapping("/disponibles")
    public List<BookResponse> getAvailableBook() {
        return bookServices.getAvailableBooks();
    }

    @Operation(summary = "Obtener libros prestados", description = "Retorna una lista de todos los libros que están actualmente prestados")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista de libros prestados obtenida exitosamente")
    })
    @GetMapping("/prestados")
    public List<BookResponse> getLoanedBooks() {
        return bookServices.getLoanedBooks();
    }

    @Operation(summary = "Buscar libros por género", description = "Retorna una lista de libros filtrados por género")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista de libros por género obtenida exitosamente")
    })
    @GetMapping("/genero")
    public List<BookResponse> getGenderByBook(
        @Parameter(description = "Género de los libros a buscar") @RequestParam("g") String gender) {
        return bookServices.getGenderByBook(gender);
    }
}
