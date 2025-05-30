package co.com.andres.models.entities;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "loans")
public class Loans {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idLoan;

    @ManyToOne // @ManyToOne: Define una relación muchos a uno entre entidades
    @JoinColumn(name = "idBook") // @JoinColumn: Especifica la columna de unión para la relación
    private Books idBook;

    @ManyToOne
    @JoinColumn(name = "idUser")
    private Users idUser;

    @Column(nullable = false)
    private LocalDate loanDate;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StateLoan stateLoan = StateLoan.NOT_ACTIVE;

}
