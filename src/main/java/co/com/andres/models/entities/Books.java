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

@Entity
@Data
@Table(name = "books")
public class Books {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bookId;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String author;

    @Column(nullable = false, unique = true)
    private String isbn;

    @Column(nullable = false)
    private LocalDate yearOfPublication;

    @Column(nullable = false)
    private String gender;

    // Esta anotación @Enumerated indica que el campo state será almacenado como una cadena de texto en la base de datos   
    @Enumerated(EnumType.STRING)
    // La anotación columnDefinition define la estructura completa de la columna en la base de datos
    // En este caso, especifica que será un varchar(10) con valor por defecto 'AVAILABLE'
    @Column(nullable = false)
    private StateBook state = StateBook.AVAILABLE;

}
