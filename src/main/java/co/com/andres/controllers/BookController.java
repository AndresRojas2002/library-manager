package co.com.andres.controllers;


import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
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

    // crea libro
    @PostMapping
    public BookResponse createBook(@RequestBody BookRequest bookRequest) {
        return bookServices.createBook(bookRequest);
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

    @PutMapping("{id}")
    public BookResponse updateBook(@PathVariable("id") long id, @RequestBody BookRequest bookRequest) {
        return bookServices.updateById(id, bookRequest);
    }

    @DeleteMapping("{id}")
    public BookResponse deleteBook(@PathVariable("id") long id) {
        return bookServices.deleteById(id);
    }

    // buscar por autor o titulo
    @GetMapping("textoBusqueda")
    public List<BookResponse> getByAuthorOrTitle(@RequestParam("q") String text) {
        return bookServices.getByAuthorOrTitle(text);
    }

}
