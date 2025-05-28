package co.com.andres.services;

import java.util.List;

import co.com.andres.models.dto.UserRequest;
import co.com.andres.models.dto.UserResponse;

public interface UserServices {

    // crea un usuario
    UserResponse createUser(UserRequest userRequest);

    // odtener todos los usuarios
    List<UserResponse> getAllUser();

    // odtener usuario por id
    UserResponse getByIdUser(long idUser);

    // borrar usuario por id
    UserResponse deleteUser(long idUser);

    // odtener usuarios por nombre o apellido
    List<UserResponse> getByNameOrLastName(String text);

    // actualizar usuario
    UserResponse updateUser(Long idUser, UserRequest userRequest);

    // usuarios con prestamo
    List<UserResponse> getWithLoanUser();

    // usuarios sin prestamos
    List<UserResponse> getWithoutLoanUser();
}
