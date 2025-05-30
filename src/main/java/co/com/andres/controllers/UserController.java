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

/**
 * Controlador REST para la gestión de usuarios de la biblioteca.
 * Proporciona endpoints para crear, leer, actualizar y eliminar usuarios,
 * así como para realizar búsquedas y filtrar por estado de préstamos.
 */
@Tag(name = "Usuarios", description = "API para gestionar los usuarios de la biblioteca")
@RestController
@RequestMapping("/api/usuarios")
@RequiredArgsConstructor
public class UserController {

    private final UserServices userServices;

    /**
     * Crea un nuevo usuario en la biblioteca.
     * @param userRequest Objeto con los datos del usuario a crear
     * @return UserResponse con los datos del usuario creado
     * @throws 400 si los datos son inválidos
     * @throws 201 si el usuario se crea exitosamente
     */
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

    /**
     * Obtiene todos los usuarios registrados en la biblioteca.
     * @return Lista de UserResponse con todos los usuarios
     * @throws 200 si la operación es exitosa
     */
    @Operation(summary = "Obtener todos los usuarios", description = "Retorna una lista de todos los usuarios registrados")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista de usuarios obtenida exitosamente",
            content = @Content(schema = @Schema(implementation = UserResponse.class)))
    })
    @GetMapping
    public List<UserResponse> getAllUsers() {
        return userServices.getAllUser();
    }

    /**
     * Obtiene un usuario específico por su ID.
     * @param id ID del usuario a buscar
     * @return UserResponse con los datos del usuario
     * @throws 200 si el usuario se encuentra
     * @throws 404 si el usuario no existe
     */
    @Operation(summary = "Obtener usuario por ID", description = "Retorna un usuario específico basado en su ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Usuario encontrado exitosamente"),
        @ApiResponse(responseCode = "404", description = "Usuario no encontrado")
    })
    @GetMapping("{id}")
    public UserResponse getById(@Parameter(description = "ID del usuario") @PathVariable("id") Long id) {
        return userServices.getByIdUser(id);
    }

    /**
     * Actualiza la información de un usuario existente.
     * @param id ID del usuario a actualizar
     * @param userRequest Objeto con los nuevos datos del usuario
     * @return UserResponse con los datos actualizados
     * @throws 200 si la actualización es exitosa
     * @throws 404 si el usuario no existe
     * @throws 400 si los datos son inválidos
     */
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

    /**
     * Elimina un usuario de la biblioteca.
     * @param id ID del usuario a eliminar
     * @return UserResponse con los datos del usuario eliminado
     * @throws 200 si la eliminación es exitosa
     * @throws 404 si el usuario no existe
     */
    @Operation(summary = "Eliminar usuario", description = "Elimina un usuario de la biblioteca")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Usuario eliminado exitosamente"),
        @ApiResponse(responseCode = "404", description = "Usuario no encontrado")
    })
    @DeleteMapping("{id}")
    public UserResponse deleteUser(@Parameter(description = "ID del usuario a eliminar") @PathVariable("id") long id) {
        return userServices.deleteUser(id);
    }

    /**
     * Busca usuarios por nombre o apellido.
     * @param texto Texto a buscar en nombre o apellido
     * @return Lista de UserResponse con los usuarios encontrados
     * @throws 200 si la búsqueda es exitosa
     */
    @Operation(summary = "Buscar usuarios por nombre o apellido", description = "Busca usuarios que coincidan con el texto proporcionado en el nombre o apellido")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Búsqueda realizada exitosamente")
    })
    @GetMapping("/buscar")
    public List<UserResponse> searchUsers(@Parameter(description = "Texto de búsqueda") @RequestParam ("u") String texto) {
        return userServices.getByNameOrLastName(texto);
    }

    /**
     * Obtiene la lista de usuarios que tienen libros prestados.
     * @return Lista de UserResponse con usuarios que tienen préstamos activos
     * @throws 200 si la operación es exitosa
     */
    @Operation(summary = "Obtener usuarios con préstamos", description = "Retorna la lista de usuarios que tienen libros prestados")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista obtenida exitosamente")
    })
    @GetMapping("/con-prestamos")
    public List<UserResponse> getUsersWithLoans() {
        return userServices.getWithLoanUser();
    }

    /**
     * Obtiene la lista de usuarios que no tienen libros prestados.
     * @return Lista de UserResponse con usuarios sin préstamos activos
     * @throws 200 si la operación es exitosa
     */
    @Operation(summary = "Obtener usuarios sin préstamos", description = "Retorna la lista de usuarios que no tienen libros prestados")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista obtenida exitosamente")
    })
    @GetMapping("/sin-prestamos")
    public List<UserResponse> getUsersWithoutLoans() {
        return userServices.getWithoutLoanUser();
    }
}

