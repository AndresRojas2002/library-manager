package co.com.andres.services.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import co.com.andres.exceptions.BooksNotFoundException;
import co.com.andres.mapper.BookMapper;
import co.com.andres.models.dto.BookRequest;
import co.com.andres.models.dto.BookResponse;
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
    private final BookMapper bookMapper;
    
   

    /**
     * Crea un nuevo libro en la biblioteca.
     * @param bookRequest Datos del libro a crear
     * @return BookResponse con los datos del libro creado
     */
    @Override
    public BookResponse createBook(BookRequest bookRequest) {
        var entity = bookMapper.toEntity(bookRequest);
        var newBook = bookRepository.save(entity);
        return bookMapper.toResponse(newBook);
    }

    /**
     * Obtiene todos los libros registrados.
     * @return Lista de BookResponse con todos los libros
     */
    @Override
    public List<BookResponse> getAll() {
        return bookRepository.findAll().stream()
                .map(bookMapper::toResponse)
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
                .map(bookMapper::toResponse)
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

        var entity = bookMapper.toEntity(bookRequest);
        entity.setBookId(entityOpcional.get().getBookId());
        entity.setState(entityOpcional.get().getState()); // No permitimos cambiar el estado aquí

        var updateEntity = bookRepository.save(entity);
        return bookMapper.toResponse(updateEntity);
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
         bookMapper.toResponse(book);
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
                .map(bookMapper::toResponse)
                .toList();
    }

    /**
     * Obtiene todos los libros disponibles para préstamo.
     * @return Lista de BookResponse con los libros disponibles
     */
    @Override
    public List<BookResponse> getAvailableBooks() {
        return bookRepository.findByState(StateBook.AVAILABLE).stream()
                .map(bookMapper::toResponse)
                .toList();
    }

    /**
     * Obtiene todos los libros que están prestados.
     * @return Lista de BookResponse con los libros prestados
     */
    @Override
    public List<BookResponse> getLoanedBooks() {
        return bookRepository.findByState(StateBook.LOANED).stream()
                .map(bookMapper::toResponse)
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
                .map(bookMapper::toResponse)
                .toList();
    }

   
}
