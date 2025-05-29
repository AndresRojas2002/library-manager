package co.com.andres.models.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Schema(description = "Respuesta con la información completa del usuario")
@Data
public class UserResponse {
    @Schema(description = "Identificador único del usuario", example = "1")
    private Long idUser;

    @Schema(description = "Nombre del usuario", example = "Luis Andres")
    private String name;

    @Schema(description = "Apellido del usuario", example = "Rojas Acevedo")
    private String lastName;

    @Schema(description = "Correo electrónico del usuario", example = "usuario@ejemplo.com")
    private String email;

    @Schema(description = "Número de teléfono del usuario", example = "3001234567")
    private String phone;

    @Schema(description = "Dirección del usuario", example = "Calle 123 #45-67, Medellín")
    private String address;

    @Schema(description = "Fecha de registro del usuario", example = "2024-03-20")
    private String dateRegistration;

    @Schema(description = "Estado actual del usuario", example = "WITHOUT_LOAN")
    private String stateUser;
}
