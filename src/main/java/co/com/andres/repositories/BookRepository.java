package co.com.andres.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import co.com.andres.models.entities.Books;
import co.com.andres.models.entities.StateBook;

public interface BookRepository extends JpaRepository<Books,Long> { 

   // Busca un libro por su ISBN
   Optional<Books> findByIsbn(String isbn);

   // Busca libros donde el texto coincida parcial o totalmente con el autor o el título (ignorando mayúsculas/minúsculas)
   List<Books> findByAuthorContainingIgnoreCaseOrTitleContainingIgnoreCase(String text, String text2);
    
   // Busca libros por estado
   List<Books> findByState(StateBook state);

   // Busca libros por género(ignorando mayúsculas/minúsculas)
   List<Books> findByGenderIgnoreCaseContaining(String gender);
}
