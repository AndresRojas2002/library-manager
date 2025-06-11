package co.com.andres.config;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

@Data

public class ApiErrorResponse {

@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")

private LocalDateTime timestamp; // Marca de tiempo que indica cuándo ocurrió el error 

private Integer status; // Código de estado HTTP
private String error; // Tipo de error
private String message; // Mensaje descriptivo del error
private String path; // Ruta de la solicitud que causó el error

    public ApiErrorResponse( HttpStatus status, String message, String path) {
        this.timestamp = LocalDateTime.now(); // odtiene la hora y la fecha actual
        this.status = status.value(); // convirte el enum httpStatus a su valor numerico
        this.error = status.getReasonPhrase(); // Obtiene la descripción del código de estado HTTP
        this.message = message; // Inicializa el mensaje descriptivo del error
        this.path = path; // Inicializa la ruta de la solicitud que causó el error
    }




    
}
