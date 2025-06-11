package co.com.andres.config;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import co.com.andres.exceptions.BookAlreadyBorrowedExeption;
import co.com.andres.exceptions.BookWinthIsbnExistExeption;
import co.com.andres.exceptions.BooksNotFoundException;
import co.com.andres.exceptions.LoansNotFoundException;
import co.com.andres.exceptions.UserNotFoundExeption;
import co.com.andres.exceptions.UserWinthGmailExeption;
import co.com.andres.exceptions.UserWinthLoanExeption;
import jakarta.servlet.http.HttpServletRequest;

@ControllerAdvice
public class GlobalExeptionHandler {

    /**
     * Maneja la excepción cuando se intenta crear un libro con un ISBN que ya
     * existe
     */
    @ExceptionHandler
    public ResponseEntity<ApiErrorResponse> handlerBookWinthIsbnExist(BookWinthIsbnExistExeption ex,
            HttpServletRequest request) {
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(new ApiErrorResponse(HttpStatus.CONFLICT, ex.getMessage(), request.getRequestURI()));
    }

    /**
     * Maneja las excepciones de tipo RuntimeException que no tienen un manejador
     * específico
     */
    @ExceptionHandler
    public ResponseEntity<ApiErrorResponse> handlerRuntimeExeption(RuntimeException ex, HttpServletRequest request) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ApiErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage(), request.getRequestURI()));
    }

    /**
     * Maneja cualquier excepción general que no tenga un manejador específico
     */
    @ExceptionHandler
    public ResponseEntity<ApiErrorResponse> handlerExeption(Exception ex, HttpServletRequest request) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ApiErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage(), request.getRequestURI()));
    }

    /**
     * Maneja la excepción cuando se intenta prestar un libro que ya está prestado
     */
    @ExceptionHandler
    public ResponseEntity<ApiErrorResponse> handlerBookAlreadyBorrowed(BookAlreadyBorrowedExeption ex,
            HttpServletRequest request) {
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(new ApiErrorResponse(HttpStatus.CONFLICT, ex.getMessage(), request.getRequestURI()));
    }

    /**
     * Maneja la excepción cuando un usuario intenta prestar un libro teniendo ya
     * uno prestado
     */
    @ExceptionHandler
    public ResponseEntity<ApiErrorResponse> handlerUserWinthLoan(UserWinthLoanExeption ex, HttpServletRequest request) {
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(new ApiErrorResponse(HttpStatus.CONFLICT, ex.getMessage(), request.getRequestURI()));
    }

    /**
     * Maneja la excepción cuando se intenta registrar un usuario con un correo
     * electrónico ya existente
     */
    @ExceptionHandler
    public ResponseEntity<ApiErrorResponse> handlerUserWinthGmail(UserWinthGmailExeption ex,
            HttpServletRequest request) {
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(new ApiErrorResponse(HttpStatus.CONFLICT, ex.getMessage(), request.getRequestURI()));
    }

    /**
     * Maneja la excepción cuando no se encuentra un libro
     */
    @ExceptionHandler
    public ResponseEntity<ApiErrorResponse> handlerBooksNotFound(BooksNotFoundException ex,
            HttpServletRequest request) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ApiErrorResponse(HttpStatus.NOT_FOUND, ex.getMessage(), request.getRequestURI()));
    }

    /**
     * Maneja la excepción cuando no se encuentra un préstamo
     */
    @ExceptionHandler
    public ResponseEntity<ApiErrorResponse> handlerLoansNotFound(LoansNotFoundException ex,
            HttpServletRequest request) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ApiErrorResponse(HttpStatus.NOT_FOUND, ex.getMessage(), request.getRequestURI()));
    }

    /**
     * Maneja la excepción cuando no se encuentra un usuario
     */
    @ExceptionHandler
    public ResponseEntity<ApiErrorResponse> handlerUserNotFound(UserNotFoundExeption ex, HttpServletRequest request) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ApiErrorResponse(HttpStatus.NOT_FOUND, ex.getMessage(), request.getRequestURI()));
    }

}
