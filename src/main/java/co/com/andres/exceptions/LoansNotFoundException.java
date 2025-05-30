package co.com.andres.exceptions;

public class LoansNotFoundException extends RuntimeException{
    public LoansNotFoundException(String message) {
        super(message);
    }
    
}
