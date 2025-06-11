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

@ControllerAdvice
public class GlobalExeptionHandler {

    /**
     * Maneja la excepción cuando se intenta crear un libro con un ISBN que ya existe
     */
    @ExceptionHandler
    public ResponseEntity<Void> handlerBookWinthIsbnExist(BookWinthIsbnExistExeption ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(null);
    }

    /**
     * Maneja las excepciones de tipo RuntimeException que no tienen un manejador específico
     */
    @ExceptionHandler
    public ResponseEntity<Void> handlerRuntimeExeption(RuntimeException ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
    }

    /**
     * Maneja cualquier excepción general que no tenga un manejador específico
     */
    @ExceptionHandler
    public ResponseEntity<Void> handlerExeption(Exception ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
    }

    /**
     * Maneja la excepción cuando se intenta prestar un libro que ya está prestado
     */
    @ExceptionHandler
    public ResponseEntity<Void> handlerBookAlreadyBorrowed(BookAlreadyBorrowedExeption ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(null);
    }

    /**
     * Maneja la excepción cuando un usuario intenta prestar un libro teniendo ya uno prestado
     */
    @ExceptionHandler
    public ResponseEntity<Void> handlerUserWinthLoan(UserWinthLoanExeption ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(null);
    }

    /**
     * Maneja la excepción cuando se intenta registrar un usuario con un correo electrónico ya existente
     */
    @ExceptionHandler
    public ResponseEntity<Void> handlerUserWinthGmail(UserWinthGmailExeption ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(null);
    }

    /**
     * Maneja la excepción cuando no se encuentra un libro
     */
    @ExceptionHandler
    public ResponseEntity<Void> handlerBooksNotFound(BooksNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
    }

    /**
     * Maneja la excepción cuando no se encuentra un préstamo
     */
    @ExceptionHandler
    public ResponseEntity<Void> handlerLoansNotFound(LoansNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
    }

    /**
     * Maneja la excepción cuando no se encuentra un usuario
     */
    @ExceptionHandler
    public ResponseEntity<Void> handlerUserNotFound(UserNotFoundExeption ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
    }

}
