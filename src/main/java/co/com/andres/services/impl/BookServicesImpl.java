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

    @Override
    public BookResponse createBook(BookRequest bookRequest) {
        var entity = toEntity(bookRequest);

        var newBook = bookRepository.save(entity);

        return toResponse(newBook);
    }

    @Override
    public List<BookResponse> getAll() {
        return bookRepository.findAll().stream()
                .map(this::toResponse)
                .toList();
    }

    @Override
    public BookResponse getById(long id) {
        return bookRepository.findById(id)
                .map(this::toResponse)
                .orElseThrow(() -> new BooksNotFoundException("NO SE PUDO ENCONTRAR EL LIBRO CON ESTE ID"));

    }

    @Override
    public BookResponse updateById(long id, BookRequest bookRequest) {
        var entityOpcional = bookRepository.findById(id);
        if (!entityOpcional.isPresent()) {
            throw new BooksNotFoundException("NO SE ENCONTRO EL LIBRO CON ESE ID ");
        }

        var entity = toEntity(bookRequest);
        entity.setBookId(entityOpcional.get().getBookId());

        var updateEntity = bookRepository.save(entity);

        return toResponse(updateEntity);
    }

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

    @Override
    public List<BookResponse> getByAuthorOrTitle(String text) {
        return bookRepository.findByAuthorContainingIgnoreCaseOrTitleContainingIgnoreCase(text, text)
                .stream()
                .map(this::toResponse)
                .toList();
    }

    @Override
    public List<BookResponse> getAvailableBooks() {
        return bookRepository.findByStatus(State.AVAILABLE).stream()
            .map(this::toResponse)
            .toList();
    }
    
    @Override
    public BookResponse loanBook(Long id) {
        var bookOptional = bookRepository.findById(id);
        if (!bookOptional.isPresent()) {
            throw new BooksNotFoundException("NO SE ENCONTRO EL LIBRO CON ESE ID ");
        }
        Books book = bookOptional.get();

        if (book.getState() == State.){
            throw new BooksNotFoundException("EL LIBRO YA SE ENCUENTRA PRESTADO  ");
        }


       
    }
    
    

    private BookResponse toResponse(Books books) {
        var response = new BookResponse();
        response.setBookId(books.getBookId());
        response.setAuthor(books.getAuthor());
        response.setTitle(books.getTitle());
        response.setIsbn(books.getIsbn());
        response.setYearOfPublication(books.getYearOfPublication().format(formatter));
        response.setGender(books.getGender());
        return response;

    }

    private Books toEntity(BookRequest bookRequest) {
        var entity = new Books();
        entity.setTitle(bookRequest.getTitle());
        entity.setAuthor(bookRequest.getAuthor());
        entity.setIsbn(bookRequest.getIsbn());
        entity.setYearOfPublication(LocalDate.parse(bookRequest.getYearOfPublication(), formatter));
        entity.setGender(bookRequest.getGender());

        return entity;
    }
}
