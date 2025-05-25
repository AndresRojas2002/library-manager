package co.com.andres.exceptions;

public class BooksNotFoundException  extends RuntimeException{

    public BooksNotFoundException ( String message){
        super(message);
    }
    
}
