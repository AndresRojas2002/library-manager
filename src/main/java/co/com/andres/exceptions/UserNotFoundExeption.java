package co.com.andres.exceptions;



//@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "No se encontro el usuario con ese id")
public class UserNotFoundExeption extends RuntimeException{

    public UserNotFoundExeption() {
        super("NO SE ENCONTRO EL USUARIO CON ESE ID");
    }
    
}
