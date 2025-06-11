package co.com.andres.models.dto;

import co.com.andres.models.entities.Books;
import co.com.andres.models.entities.Users;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class LoanResponse {
    @Schema(description = "Identificador único del préstamo", example = "1")
    private Long idLoan;

    @Schema(description = "Información del libro prestado")
    private Books idBook;

    @Schema(description = "Información del usuario que realizó el préstamo")
    private Users idUser;

    @Schema(description = "Fecha del préstamo en formato YYYY-MM-DD", example = "2024-03-20")
    private String loanDate;

    @Schema(description = "Estado actual del préstamo (ACTIVE o NOT_ACTIVE)", example = "NO ACTIVE")
    private String stateLoan;
}
