package co.com.andres.services.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import co.com.andres.exceptions.UserNotFoundExeption;
import co.com.andres.mapper.UserMapper;
import co.com.andres.models.dto.UserRequest;
import co.com.andres.models.dto.UserResponse;
import co.com.andres.models.entities.StateUser;
import co.com.andres.repositories.UserRepository;
import co.com.andres.services.UserServices;
import lombok.RequiredArgsConstructor;

/**
 * Implementación del servicio de gestión de usuarios.
 * Proporciona la lógica de negocio para todas las operaciones relacionadas con
 * usuarios,
 * incluyendo registro, consulta y actualización de información.
 */
@Service
@RequiredArgsConstructor
public class UserServicesImpl implements UserServices {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    /**
     * Crea un nuevo usuario en el sistema.
     * 
     * @param userRequest Datos del usuario a crear
     * @return UserResponse con los datos del usuario creado
     */
    @Override
    public UserResponse createUser(UserRequest userRequest) {
        var entity = userMapper.toEntity(userRequest);
        var newUser = userRepository.save(entity);
        return userMapper.toResponse(newUser);
    }

    /**
     * Obtiene todos los usuarios registrados.
     * 
     * @return Lista de UserResponse con todos los usuarios
     */
    @Override
    public List<UserResponse> getAllUser() {

        return userRepository.findAll().stream()
                .map(userMapper::toResponse)
                .toList();
    }

    /**
     * Obtiene un usuario específico por su ID.
     * 
     * @param idUser ID del usuario a buscar
     * @return UserResponse con los datos del usuario
     * @throws UserNotFoundExeption si el usuario no existe
     */
    @Override
    public UserResponse getByIdUser(long idUser) {
        return userRepository.findById(idUser)
                .map(userMapper::toResponse)
                .orElseThrow(() -> new UserNotFoundExeption("NO SE PUDO ENCONTRAR EL USUARIO POR ESE ID"));
    }

    /**
     * Elimina un usuario del sistema.
     * 
     * @param idUser ID del usuario a eliminar
     * @return UserResponse con los datos del usuario eliminado
     * @throws UserNotFoundExeption si el usuario no existe
     */
    @Override
    public void deleteUser(long idUser) {
        var optionalUser = userRepository.findById(idUser);
        if (!optionalUser.isPresent()) {
            throw new UserNotFoundExeption("NO SE PUDO ENCONTRAR EL USUARIO POR ESE ID");

        }

        var user = optionalUser.get();
        userRepository.delete(user);
        userMapper.toResponse(user);
    }

    /**
     * Busca usuarios por nombre o apellido.
     * 
     * @param text Texto a buscar
     * @return Lista de UserResponse con los usuarios encontrados
     */
    @Override
    public List<UserResponse> getByNameOrLastName(String text) {
        return userRepository.findByNameContainingIgnoreCaseOrLastNameContainingIgnoreCase(text, text)
                .stream()
                .map(userMapper::toResponse)
                .toList();
    }

    /**
     * Actualiza la información de un usuario existente.
     * 
     * @param idUser      ID del usuario a actualizar
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

        var entity = userMapper.toEntity(userRequest);
        entity.setIdUser(optionalUser.get().getIdUser());// Se mantiene el ID del usuario existente
        entity.setStateUser(optionalUser.get().getStateUser());// Se mantiene el estado actual del usuario

        var updateEntity = userRepository.save(entity);

        return userMapper.toResponse(updateEntity);
    }

    /**
     * Obtiene todos los usuarios que tienen préstamos activos.
     * 
     * @return Lista de UserResponse con los usuarios que tienen préstamos
     */
    @Override
    public List<UserResponse> getWithLoanUser() {
        return userRepository.findByStateUser(StateUser.WITH_LOAN).stream()
                .map(userMapper::toResponse)
                .toList();
    }

    /**
     * Obtiene todos los usuarios que no tienen préstamos activos.
     * 
     * @return Lista de UserResponse con los usuarios sin préstamos
     */
    @Override
    public List<UserResponse> getWithoutLoanUser() {
        return userRepository.findByStateUser(StateUser.WITHOUT_LOAN).stream()
                .map(userMapper::toResponse)
                .toList();

    }



}
