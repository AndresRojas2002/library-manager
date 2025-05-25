package co.com.andres.services.impl;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.stereotype.Service;

import co.com.andres.exceptions.BooksNotFoundException;
import co.com.andres.models.dto.BookRequest;
import co.com.andres.models.dto.BookResponse;
import co.com.andres.models.entities.Books;
import co.com.andres.models.entities.State;
import co.com.andres.repositories.BookRepository;
import co.com.andres.services.BookServices;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BookServicesImpl implements BookServices {

    private final BookRepository bookRepository;

    private DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE;

    // crea un libro
    @Override
    public BookResponse createBook(BookRequest bookRequest) {
        var entity = toEntity(bookRequest);

        var newBook = bookRepository.save(entity);

        return toResponse(newBook);
    }

    // odtine todos los libros
    @Override
    public List<BookResponse> getAll() {
        return bookRepository.findAll().stream()
                .map(this::toResponse)
                .toList();
    }

    // odtine por id
    @Override
    public BookResponse getById(long id) {
        return bookRepository.findById(id)
                .map(this::toResponse)
                .orElseThrow(() -> new BooksNotFoundException("NO SE PUDO ENCONTRAR EL LIBRO CON ESTE ID"));

    }

    // actualiza por id
    @Override
    public BookResponse updateById(long id, BookRequest bookRequest) {
        var entityOpcional = bookRepository.findById(id);
        if (!entityOpcional.isPresent()) {
            throw new BooksNotFoundException("NO SE ENCONTRO EL LIBRO CON ESE ID ");
        }

        var entity = toEntity(bookRequest);
        entity.setBookId(entityOpcional.get().getBookId());
        entity.setState(entityOpcional.get().getState()); // No permitimos cambiar el estado aquí

        var updateEntity = bookRepository.save(entity);

        return toResponse(updateEntity);
    }

    // elimina por id
    @Override
    public BookResponse deleteById(long id) {

        var optionalBook = bookRepository.findById(id);
        if (!optionalBook.isPresent()) {
            throw new BooksNotFoundException("NO SE ENCONTRO EL LIBRO CON ESE ID ");
        }
        var book = optionalBook.get();
        bookRepository.delete(book);
        return toResponse(book);
    }

    // odtine por autor o titulo
    @Override
    public List<BookResponse> getByAuthorOrTitle(String text) {
        return bookRepository.findByAuthorContainingIgnoreCaseOrTitleContainingIgnoreCase(text, text)
                .stream()
                .map(this::toResponse)
                .toList();
    }

    // odtine libros disponibles
    @Override
    public List<BookResponse> getAvailableBooks() {
        return bookRepository.findByState(State.AVAILABLE).stream()
            .map(this::toResponse)
            .toList();
    }

    // odtine libros prestados
    @Override
    public List<BookResponse> getLoanedBooks() {
        return bookRepository.findByState(State.LOANED).stream()
            .map(this::toResponse)
            .toList();
    }

    // presta un libro
    @Override
    public BookResponse loanBook(Long id) {
        var bookOptional = bookRepository.findById(id);
        if (!bookOptional.isPresent()) {
            throw new BooksNotFoundException("NO SE ENCONTRO EL LIBRO CON ESE ID ");
        }
        Books book = bookOptional.get();

        if (book.getState() == State.AVAILABLE){
            book.setState (State.LOANED);
            bookRepository.save(book);
            return toResponse(book);
        }else {throw new BooksNotFoundException("EL LIBRO YA SE ENCUENTRA PRESTADO  ");}


       
    }

    // devuelve un libro
    @Override
    public BookResponse returnBook(Long id) {
        var bookOptional = bookRepository.findById(id);
        if (!bookOptional.isPresent()) {
            throw new BooksNotFoundException("NO SE ENCONTRO EL LIBRO CON ESE ID ");
        }
        Books book = bookOptional.get();

        if (book.getState() == State.LOANED){
            book.setState (State.AVAILABLE);
            bookRepository.save(book);
            return toResponse(book);
        }else {throw new BooksNotFoundException("EL LIBRO YA SE ENCUENTRA DISPONIBLE");}
    }

    // listar por genero
    @Override
    public List<BookResponse> getGenderByBook(String gender) {
        return bookRepository.findByGenderIgnoreCaseContaining(gender).stream()
            .map(this::toResponse)
            .toList();
    }
    
    
    // convierte un libro a una respuesta
    private BookResponse toResponse(Books books) {
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

    // convierte una peticion de libro a una entidad
    private Books toEntity(BookRequest bookRequest) {
        var entity = new Books();
        entity.setTitle(bookRequest.getTitle());
        entity.setAuthor(bookRequest.getAuthor());
        entity.setIsbn(bookRequest.getIsbn());
        entity.setYearOfPublication(LocalDate.parse(bookRequest.getYearOfPublication(), formatter));
        entity.setGender(bookRequest.getGender());
        entity.setState(State.AVAILABLE);
        return entity;
    }
}
