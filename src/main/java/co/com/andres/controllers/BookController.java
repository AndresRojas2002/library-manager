package co.com.andres.controllers;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import co.com.andres.models.dto.BookRequest;
import co.com.andres.models.dto.BookResponse;
import co.com.andres.services.BookServices;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/libros")
@RequiredArgsConstructor
public class BookController {

    private final BookServices bookServices;

    // crea varios libros

    @PostMapping
    public List<BookResponse> createBooks(@RequestBody List<BookRequest> bookRequests) {
        return bookRequests.stream()
                .map(bookServices::createBook)
                .toList();
    }

    // odtiene todos los libros
    @GetMapping
    public List<BookResponse> getAllBooks() {
        return bookServices.getAll();
    }

    // odtine por id
    @GetMapping("{id}")
    public BookResponse getById(@PathVariable("id") Long id) {
        return bookServices.getById(id);

    }

    // actualiza por id
    @PutMapping("{id}")
    public BookResponse updateBook(@PathVariable("id") long id, @RequestBody BookRequest bookRequest) {
        return bookServices.updateById(id, bookRequest);
    }

    // elimina por id
    @DeleteMapping("{id}")
    public BookResponse deleteBook(@PathVariable("id") long id) {
        return bookServices.deleteById(id);
    }

    // buscar por autor o titulo
    @GetMapping("/textoBusqueda")
    public List<BookResponse> getByAuthorOrTitle(@RequestParam("q") String text) {
        return bookServices.getByAuthorOrTitle(text);
    }

    // lista de libros disponibles
    @GetMapping("/disponibles")
    public List<BookResponse> getAvailableBook() {
        return bookServices.getAvailableBooks();
    }

    // lista de libros prestados
    @GetMapping("/prestados")
    public List<BookResponse> getLoanedBooks() {
        return bookServices.getLoanedBooks();
    }

    // presta un libro
    @PatchMapping("/{id}/prestar")
    public BookResponse loanBook(@PathVariable("id") long id) {
        return bookServices.loanBook(id);
    }

    // devuelve libro
    @PatchMapping("/{id}/devolver")
    public BookResponse returnBook(@PathVariable("id") long id) {
        return bookServices.returnBook(id);
    }

    // listar por genero
    @GetMapping("/genero")
    public List<BookResponse> getGenderByBook(@RequestParam("g") String gender) {
        return bookServices.getGenderByBook(gender);
    }

}
