package co.com.andres.mapper;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.springframework.stereotype.Component;

import co.com.andres.models.dto.BookRequest;
import co.com.andres.models.dto.BookResponse;
import co.com.andres.models.entities.Books;
import co.com.andres.models.entities.StateBook;


@Component
public class BookMapper {


    private DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE;
     /**
     * Convierte una entidad Books a un DTO BookResponse.
     * @param books Entidad a convertir
     * @return BookResponse con los datos del libro
     */
    public BookResponse toResponse(Books books) {
        var response = new BookResponse();
        response.setBookId(books.getBookId());
        response.setAuthor(books.getAuthor());
        response.setTitle(books.getTitle());
        response.setIsbn(books.getIsbn());
        response.setYearOfPublication(books.getYearOfPublication().format(formatter));
        response.setGender(books.getGender());
        response.setState(books.getState().toString());
        return response;
    }

    /**
     * Convierte un DTO BookRequest a una entidad Books.
     * @param bookRequest DTO a convertir
     * @return Books con los datos del libro
     */
    public Books toEntity(BookRequest bookRequest) {
        var entity = new Books();
        entity.setTitle(bookRequest.getTitle());
        entity.setAuthor(bookRequest.getAuthor());
        entity.setIsbn(bookRequest.getIsbn());
        entity.setYearOfPublication(LocalDate.parse(bookRequest.getYearOfPublication(), formatter));
        entity.setGender(bookRequest.getGender());
        entity.setState(StateBook.AVAILABLE);
        return entity;
    }
    
}
