package co.com.andres.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import co.com.andres.models.dto.LoanResponse;
import co.com.andres.models.entities.Loans;
import co.com.andres.services.LoanServices;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

/**
 * Controlador REST para la gestión de préstamos de la biblioteca.
 * Proporciona endpoints para crear, consultar, eliminar y gestionar devoluciones de préstamos.
 * Incluye validaciones para asegurar que los préstamos cumplan con las reglas de negocio.
 */
@Tag(name = "Préstamos", description = "API para gestionar los préstamos de la biblioteca")
@RestController
@RequestMapping("/api/prestamos")
@RequiredArgsConstructor
public class LoanController {

    private final LoanServices loanServices;

    /**
     * Obtiene todos los préstamos activos en la biblioteca.
     * @return Lista de LoanResponse con todos los préstamos
     * @throws 200 si la operación es exitosa
     */
    @Operation(summary = "Obtener todos los préstamos", description = "Retorna una lista de todos los préstamos activos en la biblioteca")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista de préstamos obtenida exitosamente",
            content = @Content(schema = @Schema(implementation = LoanResponse.class)))
    })
    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    public List<LoanResponse> getAllLoan() {
        return loanServices.getAllLoan();
    }

    /**
     * Elimina un préstamo específico de la biblioteca.
     * @param idLoan ID del préstamo a eliminar
     * @return LoanResponse con los datos del préstamo eliminado
     * @throws 200 si la eliminación es exitosa
     * @throws 404 si el préstamo no existe
     */
    @Operation(summary = "Eliminar préstamo", description = "Elimina un préstamo de la biblioteca")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Préstamo eliminado exitosamente"),
        @ApiResponse(responseCode = "404", description = "Préstamo no encontrado")
    })
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("{id}")
    public void deleteLoan(
        @Parameter(description = "ID del préstamo a eliminar") @PathVariable("id") Long idLoan) {
         loanServices.deleteLoan(idLoan);
    }

    /**
     * Crea un nuevo préstamo asociando un libro a un usuario.
     * Valida que el libro esté disponible y el usuario no tenga préstamos activos.
     * @param userId ID del usuario que solicita el préstamo
     * @param bookId ID del libro a prestar
     * @return ResponseEntity con el préstamo creado
     * @throws 200 si el préstamo se crea exitosamente
     * @throws 404 si el libro o usuario no existe
     * @throws 400 si el libro no está disponible o el usuario tiene un préstamo activo
     */
    @Operation(summary = "Crear nuevo préstamo", description = "Crea un nuevo préstamo asociando un libro a un usuario")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Préstamo creado exitosamente",
            content = @Content(schema = @Schema(implementation = Loans.class))),
        @ApiResponse(responseCode = "404", description = "Libro o usuario no encontrado"),
        @ApiResponse(responseCode = "400", description = "No se puede realizar el préstamo: libro no disponible o usuario con préstamo activo")
    })
    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/create")
    public ResponseEntity<Loans> createLoan(
        @Parameter(description = "ID del usuario que solicita el préstamo") @RequestParam Long userId,
        @Parameter(description = "ID del libro a prestar") @RequestParam Long bookId) {
        return ResponseEntity.ok(loanServices.createLoanWithUserAndBook(userId, bookId));
    }

    /**
     * Registra la devolución de un libro prestado.
     * Actualiza los estados del libro, usuario y préstamo.
     * @param loanId ID del préstamo a devolver
     * @return ResponseEntity con el préstamo actualizado
     * @throws 200 si la devolución es exitosa
     * @throws 404 si el préstamo no existe
     * @throws 400 si el préstamo ya fue devuelto o no está activo
     */
    @Operation(summary = "Devolver libro", description = "Registra la devolución de un libro prestado")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Libro devuelto exitosamente",
            content = @Content(schema = @Schema(implementation = Loans.class))),
        @ApiResponse(responseCode = "404", description = "Préstamo no encontrado"),
        @ApiResponse(responseCode = "400", description = "El préstamo ya fue devuelto o no está activo")
    })
    @ResponseStatus(HttpStatus.OK)
    @PatchMapping("/devolver/{id}")
    public ResponseEntity<Loans> returnLoan(
        @Parameter(description = "ID del préstamo a devolver") @PathVariable("id") Long loanId) {
        return ResponseEntity.ok(loanServices.returnLoan(loanId));
    }
}
