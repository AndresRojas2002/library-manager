package co.com.andres.exceptions;


public class BookAlreadyBorrowedExeption extends RuntimeException {

    public BookAlreadyBorrowedExeption() {
        super(" ESTE LIBRO YA ENCUENTRA PRESTADO");
    }
}

