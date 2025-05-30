package co.com.andres.models.entities;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "users")
public class Users {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_user", unique = true)
    private Long idUser;

    @Column(nullable = false)
    private String name;

    @Column (nullable = false)
    private String lastName;

    @Column(nullable = false)
    private String email;

    @Column
    private String phone;

    @Column(nullable = false)
    private String address;

    @Column(nullable = false)
    private LocalDate dateRegistration;

   
    // Esta anotación @Enumerated indica que el campo state será almacenado como una cadena de texto en la base de datos   
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StateUser stateUser = StateUser.WITHOUT_LOAN;

}
