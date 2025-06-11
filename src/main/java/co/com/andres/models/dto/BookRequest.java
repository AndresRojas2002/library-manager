package co.com.andres.models.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

//peticion de libro
@Schema(description = "DTO para la creación y actualización de libros")
@Data
public class BookRequest {
    @Schema(
        description = "Título del libro",
        example = "Cien años de soledad",
        required = true
    )
    @NotBlank(message = "EL CAMPO 'TITLE' NO PUEDE ESTAR VACÍO")
    @Size(min = 3, message = "EL CAMPO TITLE DEBE CONTENER AL MENOS 3 CARACTERES")
    private String title;

    @Schema(
        description = "Nombre del autor del libro",
        example = "Gabriel García Márquez",
        required = true
    )
    @NotBlank(message = "EL CAMPO 'AUTHOR' NO PUEDE ESTAR VACÍO")
    @Size(min = 5, message = "EL CAMPO AUTHOR DEBE CONTENER AL MENOS 3 CARACTERES")
    private String author;

    @Schema(
        description = "Número ISBN único del libro",
        example = "978-84-376-0494-7",
        required = true
    )
    @NotBlank(message = "EL CAMPO 'ISBN' NO PUEDE ESTAR VACÍO")
    @Size(min = 8, message = "EL CAMPO ISBN DEBE CONTENER AL MENOS 8 CARACTERES")
    private String isbn;

    @Schema(
        description = "Fecha de publicación del libro en formato YYYY-MM-DD",
        example = "1967-05-30",
        required = true,
        pattern = "^\\d{4}-\\d{2}-\\d{2}$"
    )
    @NotBlank(message = "EL CAMPO 'YEAR OF PUBLICATION' NO PUEDE ESTAR VACÍO")
    @Pattern(regexp = "^\\d{4}-\\d{2}-\\d{2}$", message = "EL FORMATO DE FECHA DEBE SER YYYY-MM-DD")
    @JsonProperty(value = "year_of_publication")
    private String yearOfPublication;

    @Schema(
        description = "Género literario del libro",
        example = "Novela",
        required = true
    )
    @NotBlank(message = "EL CAMPO 'GENDER' NO PUEDE ESTAR VACÍO")
    private String gender;

}
