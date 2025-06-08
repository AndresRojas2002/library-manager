package co.com.andres.services.impl;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.stereotype.Service;

import co.com.andres.exceptions.BooksNotFoundException;
import co.com.andres.models.dto.BookRequest;
import co.com.andres.models.dto.BookResponse;
import co.com.andres.models.entities.Books;
import co.com.andres.models.entities.StateBook;
import co.com.andres.repositories.BookRepository;
import co.com.andres.services.BookServices;
import lombok.RequiredArgsConstructor;

/**
 * Implementación del servicio de gestión de libros.
 * Proporciona la lógica de negocio para todas las operaciones relacionadas con libros,
 * incluyendo CRUD, búsquedas y gestión de préstamos.
 */
@Service
@RequiredArgsConstructor
public class BookServicesImpl implements BookServices {

    private final BookRepository bookRepository;
    private DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE;

    /**
     * Crea un nuevo libro en la biblioteca.
     * @param bookRequest Datos del libro a crear
     * @return BookResponse con los datos del libro creado
     */
    @Override
    public BookResponse createBook(BookRequest bookRequest) {
        var entity = toEntity(bookRequest);
        var newBook = bookRepository.save(entity);
        return toResponse(newBook);
    }

    /**
     * Obtiene todos los libros registrados.
     * @return Lista de BookResponse con todos los libros
     */
    @Override
    public List<BookResponse> getAll() {
        return bookRepository.findAll().stream()
                .map(this::toResponse)
                .toList();
    }

    /**
     * Obtiene un libro específico por su ID.
     * @param id ID del libro a buscar
     * @return BookResponse con los datos del libro
     * @throws BooksNotFoundException si el libro no existe
     */
    @Override
    public BookResponse getById(long id) {
        return bookRepository.findById(id)
                .map(this::toResponse)
                .orElseThrow(() -> new BooksNotFoundException("NO SE PUDO ENCONTRAR EL LIBRO CON ESTE ID"));
    }

    /**
     * Actualiza la información de un libro existente.
     * @param id ID del libro a actualizar
     * @param bookRequest Nuevos datos del libro
     * @return BookResponse con los datos actualizados
     * @throws BooksNotFoundException si el libro no existe
     */
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

    /**
     * Elimina un libro de la biblioteca.
     * @param id ID del libro a eliminar
     * @return BookResponse con los datos del libro eliminado
     * @throws BooksNotFoundException si el libro no existe
     */
    @Override
    public void deleteById(long id) {
        var optionalBook = bookRepository.findById(id);
        if (!optionalBook.isPresent()) {
            throw new BooksNotFoundException("NO SE ENCONTRO EL LIBRO CON ESE ID ");
        }
        var book = optionalBook.get();
        bookRepository.delete(book);
         toResponse(book);
    }

    /**
     * Busca libros por autor o título.
     * @param text Texto a buscar
     * @return Lista de BookResponse con los libros encontrados
     */
    @Override
    public List<BookResponse> getByAuthorOrTitle(String text) {
        return bookRepository.findByAuthorContainingIgnoreCaseOrTitleContainingIgnoreCase(text, text)
                .stream()
                .map(this::toResponse)
                .toList();
    }

    /**
     * Obtiene todos los libros disponibles para préstamo.
     * @return Lista de BookResponse con los libros disponibles
     */
    @Override
    public List<BookResponse> getAvailableBooks() {
        return bookRepository.findByState(StateBook.AVAILABLE).stream()
                .map(this::toResponse)
                .toList();
    }

    /**
     * Obtiene todos los libros que están prestados.
     * @return Lista de BookResponse con los libros prestados
     */
    @Override
    public List<BookResponse> getLoanedBooks() {
        return bookRepository.findByState(StateBook.LOANED).stream()
                .map(this::toResponse)
                .toList();
    }

    /**
     * Busca libros por género.
     * @param gender Género a buscar
     * @return Lista de BookResponse con los libros del género especificado
     */
    @Override
    public List<BookResponse> getGenderByBook(String gender) {
        return bookRepository.findByGenderIgnoreCaseContaining(gender).stream()
                .map(this::toResponse)
                .toList();
    }

    /**
     * Convierte una entidad Books a un DTO BookResponse.
     * @param books Entidad a convertir
     * @return BookResponse con los datos del libro
     */
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

    /**
     * Convierte un DTO BookRequest a una entidad Books.
     * @param bookRequest DTO a convertir
     * @return Books con los datos del libro
     */
    private Books toEntity(BookRequest bookRequest) {
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
