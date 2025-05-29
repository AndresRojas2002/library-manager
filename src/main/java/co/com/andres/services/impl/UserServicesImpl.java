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

@RequiredArgsConstructor
@Service
public class UserServicesImpl implements UserServices {
    private final UserRepository userRepository;
    private DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE;

    @Override
    public UserResponse createUser(UserRequest userRequest) {
        var entity = toEntity(userRequest);

        var newUser = userRepository.save(entity);

        return toResponse(newUser);
    }

    @Override
    public List<UserResponse> getAllUser() {

        return userRepository.findAll().stream()
                .map(this::toResponse)
                .toList();
    }

    @Override
    public UserResponse getByIdUser(long idUser) {
        return userRepository.findById(idUser)
                .map(this::toResponse)
                .orElseThrow(() -> new UserNotFoundExeption("NO SE PUDO ENCONTRAR EL USUARIO POR ESE ID"));
    }

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

    @Override
    public List<UserResponse> getByNameOrLastName(String text) {
        return userRepository.findByNameContainingIgnoreCaseOrLastNameContainingIgnoreCase(text, text)
                .stream()
                .map(this::toResponse)
                .toList();
    }

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

    @Override
    public List<UserResponse> getWithLoanUser() {
        return userRepository.findByStateUser(StateUser.WITH_LOAN).stream()
                .map(this::toResponse)
                .toList();
    }

    @Override
    public List<UserResponse> getWithoutLoanUser() {
        return userRepository.findByStateUser(StateUser.WITHOUT_LOAN).stream()
                .map(this::toResponse)
                .toList();

    }

    // convierte un usuario a una respuesta
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

    // convierte una peticion de un usuario a una entidad
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
