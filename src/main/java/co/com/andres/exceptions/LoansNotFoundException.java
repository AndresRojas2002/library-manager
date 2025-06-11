package co.com.andres.exceptions;



//@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "No se encontro el libro con ese id")
public class LoansNotFoundException extends RuntimeException{
    public LoansNotFoundException() {
        super("NO SE ENCONTRO EL PRESTAMO CON ESE ID");
    }
    
}
