package co.com.andres.models.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

//respuesta de libro
@Schema(description = "respuesta de la informacion completa de los libros")
@Data
public class BookResponse {

    @Schema(description = "Identificador único del libro", example = "1")
    private Long bookId;

    @Schema(description = "Título del libro", example = "Cien años de soledad")
    private String title;

    @Schema(description = "Nombre del autor del libro", example = "Gabriel García Márquez")
    private String author;

    @Schema(description = "Número ISBN del libro", example = "978-84-376-0494-7")
    private String isbn;

    @Schema(description = "Año de publicación del libro en formato YYYY-MM-DD", example = "1967-05-30")
    private String yearOfPublication;

    @Schema(description = "Género literario del libro", example = "Realismo mágico")
    private String gender;

    @Schema(description = "Estado actual del libro (DISPONIBLE o PRESTADO)", example = "DISPONIBLE")
    private String state;
}
