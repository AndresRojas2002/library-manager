package co.com.andres.services.impl;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.stereotype.Service;

import co.com.andres.exceptions.UserNotFoundExeption;
import co.com.andres.models.dto.UserRequest;
import co.com.andres.models.dto.UserResponse;
import co.com.andres.models.entities.StateUser;
import co.com.andres.models.entities.Users;
import co.com.andres.repositories.UserRepository;
import co.com.andres.services.UserServices;
import lombok.RequiredArgsConstructor;

/**
 * Implementación del servicio de gestión de usuarios.
 * Proporciona la lógica de negocio para todas las operaciones relacionadas con usuarios,
 * incluyendo registro, consulta y actualización de información.
 */
@Service
@RequiredArgsConstructor
public class UserServicesImpl implements UserServices {
    private final UserRepository userRepository;
    private DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE;

    /**
     * Crea un nuevo usuario en el sistema.
     * @param userRequest Datos del usuario a crear
     * @return UserResponse con los datos del usuario creado
     */
    @Override
    public UserResponse createUser(UserRequest userRequest) {
        var entity = toEntity(userRequest);
        var newUser = userRepository.save(entity);
        return toResponse(newUser);
    }

    /**
     * Obtiene todos los usuarios registrados.
     * @return Lista de UserResponse con todos los usuarios
     */
    @Override
    public List<UserResponse> getAllUser() {

        return userRepository.findAll().stream()
                .map(this::toResponse)
                .toList();
    }

    /**
     * Obtiene un usuario específico por su ID.
     * @param idUser ID del usuario a buscar
     * @return UserResponse con los datos del usuario
     * @throws UserNotFoundExeption si el usuario no existe
     */
    @Override
    public UserResponse getByIdUser(long idUser) {
        return userRepository.findById(idUser)
                .map(this::toResponse)
                .orElseThrow(() -> new UserNotFoundExeption("NO SE PUDO ENCONTRAR EL USUARIO POR ESE ID"));
    }

    /**
     * Elimina un usuario del sistema.
     * @param idUser ID del usuario a eliminar
     * @return UserResponse con los datos del usuario eliminado
     * @throws UserNotFoundExeption si el usuario no existe
     */
    @Override
    public UserResponse deleteUser(long idUser) {
        var optionalUser = userRepository.findById(idUser);
        if (!optionalUser.isPresent()) {
            throw new UserNotFoundExeption("NO SE PUDO ENCONTRAR EL USUARIO POR ESE ID");

        }

        var user = optionalUser.get();
        userRepository.delete(user);
        return toResponse(user);
    }

    /**
     * Busca usuarios por nombre o apellido.
     * @param text Texto a buscar
     * @return Lista de UserResponse con los usuarios encontrados
     */
    @Override
    public List<UserResponse> getByNameOrLastName(String text) {
        return userRepository.findByNameContainingIgnoreCaseOrLastNameContainingIgnoreCase(text, text)
                .stream()
                .map(this::toResponse)
                .toList();
    }

    /**
     * Actualiza la información de un usuario existente.
     * @param idUser ID del usuario a actualizar
     * @param userRequest Nuevos datos del usuario
     * @return UserResponse con los datos actualizados
     * @throws UserNotFoundExeption si el usuario no existe
     */
    @Override
    public UserResponse updateUser(Long idUser, UserRequest userRequest) {
        var optionalUser = userRepository.findById(idUser);
        if (!optionalUser.isPresent()) {
            throw new UserNotFoundExeption("NO SE PUDO ENCONTRAR EL USUARIO POR ESE ID");

        }

        var entity = toEntity(userRequest);
        entity.setIdUser(optionalUser.get().getIdUser());// Se mantiene el ID del usuario existente
        entity.setStateUser(optionalUser.get().getStateUser());// Se mantiene el estado actual del usuario

        var updateEntity = userRepository.save(entity);

        return toResponse(updateEntity);
    }

    /**
     * Obtiene todos los usuarios que tienen préstamos activos.
     * @return Lista de UserResponse con los usuarios que tienen préstamos
     */
    @Override
    public List<UserResponse> getWithLoanUser() {
        return userRepository.findByStateUser(StateUser.WITH_LOAN).stream()
                .map(this::toResponse)
                .toList();
    }

    /**
     * Obtiene todos los usuarios que no tienen préstamos activos.
     * @return Lista de UserResponse con los usuarios sin préstamos
     */
    @Override
    public List<UserResponse> getWithoutLoanUser() {
        return userRepository.findByStateUser(StateUser.WITHOUT_LOAN).stream()
                .map(this::toResponse)
                .toList();

    }

    /**
     * Convierte una entidad Users a un DTO UserResponse.
     * @param user Entidad a convertir
     * @return UserResponse con los datos del usuario
     */
    private UserResponse toResponse(Users user) {
        var response = new UserResponse();
        response.setIdUser(user.getIdUser());
        response.setName(user.getName());
        response.setLastName(user.getLastName());
        response.setEmail(user.getEmail());
        response.setAddress(user.getAddress());
        response.setPhone(user.getPhone());
        response.setDateRegistration(user.getDateRegistration().format(formatter));
        response.setStateUser(user.getStateUser().toString());
        return response;

    }

    /**
     * Convierte un DTO UserRequest a una entidad Users.
     * @param userRequest DTO a convertir
     * @return Users con los datos del usuario
     */
    private Users toEntity(UserRequest userRequest) {
        var user = new Users();
        user.setName(userRequest.getName());
        user.setLastName(userRequest.getLastName());
        user.setEmail(userRequest.getEmail());
        user.setAddress(userRequest.getAddress());
        user.setPhone(userRequest.getPhone());
        user.setDateRegistration(LocalDate.parse(userRequest.getDateRegistration(), formatter));
        user.setStateUser(StateUser.WITHOUT_LOAN);
        return user;
    }

}
