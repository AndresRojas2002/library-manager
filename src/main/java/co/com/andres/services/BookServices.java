package co.com.andres.services;

import java.util.List;

import co.com.andres.models.dto.BookRequest;
import co.com.andres.models.dto.BookResponse;

public interface BookServices {
    // crea un libro
    BookResponse createBook(BookRequest bookRequest);

    // odtine todos los libros
    List<BookResponse> getAll();

    // odtiene libro por id
    BookResponse getById(long id);

    // actualiza libro
    BookResponse updateById(long id, BookRequest bookRequest);

    // borra libro
    BookResponse deleteById(long id);

    // odtine libros por autor o titulo
    List<BookResponse> getByAuthorOrTitle(String text);

    // Devuelve todos los libros que están disponibles
    List<BookResponse> getAvailableBooks();

    // Devuelve todos los libros que están prestados
    List<BookResponse> getLoanedBooks();

    // Prestar un libro
    BookResponse loanBook(Long Id);

    // Devolver libro
    BookResponse returnBook(Long Id);

    // listar por genero 
    List<BookResponse> getGenderByBook(String gender);
}
