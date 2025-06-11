package co.com.andres.mapper;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.springframework.stereotype.Component;

import co.com.andres.models.dto.UserRequest;
import co.com.andres.models.dto.UserResponse;
import co.com.andres.models.entities.StateUser;
import co.com.andres.models.entities.Users;

@Component
public class UserMapper {
    private DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE;

    /**
     * Convierte una entidad Users a un DTO UserResponse.
     * 
     * @param user Entidad a convertir
     * @return UserResponse con los datos del usuario
     */
    public UserResponse toResponse(Users user) {
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
     * 
     * @param userRequest DTO a convertir
     * @return Users con los datos del usuario
     */
    public Users toEntity(UserRequest userRequest) {
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
