package co.com.andres.models.dto;

import co.com.andres.models.entities.Books;
import co.com.andres.models.entities.Users;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
@Data
public class LoanRequest {

    @Schema(description = "ID del libro a prestar", example = "1", required = true)
    @NotNull(message = "EL CAMPO 'IDBOOK' NO PUEDE ESTAR VACÍO")
    private Books idBook;

    @Schema(description = "ID del usuario que solicita el préstamo", example = "1", required = true)
    @NotNull(message = "EL CAMPO 'IDUSER' NO PUEDE ESTAR VACÍO")
    private Users idUser;
}
