package co.com.andres.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;


import co.com.andres.models.entities.StateUser;
import co.com.andres.models.entities.Users;

public interface UserRepository extends JpaRepository<Users, Long> {

    // busca libro por estado
    List<Users> findByStateUser(StateUser stateUser);

    //buscar por nombre o apellido
    List<Users> findByNameContainingIgnoreCaseOrLastNameContainingIgnoreCase(String text, String text2);

}
