package co.com.andres.exceptions;

public class UserNotFoundExeption extends RuntimeException{

    public UserNotFoundExeption(String message) {
        super(message);
    }
    
}
