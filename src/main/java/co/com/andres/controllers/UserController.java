
package co.com.andres.controllers;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import co.com.andres.models.dto.UserRequest;
import co.com.andres.models.dto.UserResponse;
import co.com.andres.services.UserServices;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;

@Tag(name = "Usuarios", description = "API para gestionar los usuarios de la biblioteca")
@RestController
@RequestMapping("/api/usuarios")
@RequiredArgsConstructor
public class UserController {

    private final UserServices userServices;

    @Operation(summary = "Crear un nuevo usuario", description = "Crea un nuevo usuario en la biblioteca")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Usuario creado exitosamente",
            content = @Content(schema = @Schema(implementation = UserResponse.class))),
        @ApiResponse(responseCode = "400", description = "Datos del usuario inválidos")
    })
    @PostMapping
    public UserResponse createUser(@Valid @RequestBody UserRequest userRequest) {
        return userServices.createUser(userRequest);
    }

    @Operation(summary = "Obtener todos los usuarios", description = "Retorna una lista de todos los usuarios registrados")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista de usuarios obtenida exitosamente",
            content = @Content(schema = @Schema(implementation = UserResponse.class)))
    })
    @GetMapping
    public List<UserResponse> getAllUsers() {
        return userServices.getAllUser();
    }

    @Operation(summary = "Obtener usuario por ID", description = "Retorna un usuario específico basado en su ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Usuario encontrado exitosamente"),
        @ApiResponse(responseCode = "404", description = "Usuario no encontrado")
    })
    @GetMapping("{id}")
    public UserResponse getById(@Parameter(description = "ID del usuario") @PathVariable("id") Long id) {
        return userServices.getByIdUser(id);
    }

    @Operation(summary = "Actualizar usuario", description = "Actualiza la información de un usuario existente")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Usuario actualizado exitosamente"),
        @ApiResponse(responseCode = "404", description = "Usuario no encontrado"),
        @ApiResponse(responseCode = "400", description = "Datos del usuario inválidos")
    })
    @PutMapping("{id}")
    public UserResponse updateUser(
        @Parameter(description = "ID del usuario a actualizar") @Valid @Min(0) @PathVariable("id") long id,
        @Valid @RequestBody UserRequest userRequest) {
        return userServices.updateUser(id, userRequest);
    }

    @Operation(summary = "Eliminar usuario", description = "Elimina un usuario de la biblioteca")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Usuario eliminado exitosamente"),
        @ApiResponse(responseCode = "404", description = "Usuario no encontrado")
    })
    @DeleteMapping("{id}")
    public UserResponse deleteUser(@Parameter(description = "ID del usuario a eliminar") @PathVariable("id") long id) {
        return userServices.deleteUser(id);
    }

    @Operation(summary = "Buscar usuarios por nombre o apellido", description = "Busca usuarios que coincidan con el texto proporcionado en el nombre o apellido")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Búsqueda realizada exitosamente")
    })
    @GetMapping("/buscar")
    public List<UserResponse> searchUsers(@Parameter(description = "Texto de búsqueda") @RequestParam ("u") String texto) {
        return userServices.getByNameOrLastName(texto);
    }

    @Operation(summary = "Obtener usuarios con préstamos", description = "Retorna la lista de usuarios que tienen libros prestados")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista obtenida exitosamente")
    })
    @GetMapping("/con-prestamos")
    public List<UserResponse> getUsersWithLoans() {
        return userServices.getWithLoanUser();
    }

    @Operation(summary = "Obtener usuarios sin préstamos", description = "Retorna la lista de usuarios que no tienen libros prestados")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista obtenida exitosamente")
    })
    @GetMapping("/sin-prestamos")
    public List<UserResponse> getUsersWithoutLoans() {
        return userServices.getWithoutLoanUser();
    }
}

