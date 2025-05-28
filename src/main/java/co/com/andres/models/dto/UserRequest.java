package co.com.andres.models.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Schema(description = "DTO para la creación y actualización de usuarios")
@Data
public class UserRequest {

    

    @Schema(description = "Nombre del usuario", example = "Luis Andres", required = true)
    @NotBlank(message = "El nombre es obligatorio")
    @Size(min = 2, max = 50, message = "El nombre debe tener entre 2 y 50 caracteres")
    private String name;

    @Schema(description = "Apellido del usuario", example = "Rojas Acevedo", required = false)
    @Size(min = 2, max = 50, message = "El apellido debe tener entre 2 y 50 caracteres")
    private String lastName;

    @Schema(description = "Correo electrónico del usuario", example = "usuario@ejemplo.com", required = true)
    @NotBlank(message = "El correo electrónico es obligatorio")
    @Email(message = "El formato del correo electrónico no es válido")
    @Size(max = 100, message = "El correo electrónico no debe exceder los 100 caracteres")
    private String email;

    @Schema(description = "Número de teléfono del usuario", example = "3001234567", required = false)
    @Size(min = 7, max = 15, message = "El número de teléfono debe tener entre 7 y 15 caracteres")
    private String phone;

    @Schema(description = "Fecha de registro del usuario en formato YYYY-MM-DD", example = "2024-03-20", required = true, pattern = "^\\d{4}-\\d{2}-\\d{2}$")
    @NotBlank(message = "La fecha de registro es obligatoria")
    @Pattern(regexp = "^\\d{4}-\\d{2}-\\d{2}$", message = "El formato de fecha debe ser YYYY-MM-DD")
    private String dateRegistration;

    @Schema(description = "Dirección del usuario", example = "Calle 123 #45-67, Medellin", required = true)
    @NotBlank(message = "La dirección es obligatoria")
    private String address;
}
