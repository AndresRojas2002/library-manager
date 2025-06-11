package co.com.andres.exceptions;


public class UserWinthLoanExeption extends RuntimeException {

    public UserWinthLoanExeption() {
        super("EL USUARIO YA TIENE UN LIBRO PRESTADO");
    }

  
}

