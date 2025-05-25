package co.com.andres.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import co.com.andres.models.entities.Books;
import co.com.andres.models.entities.State;

public interface BookRepository extends JpaRepository<Books,Long> { 

   // Busca libros donde el texto coincida parcial o totalmente con el autor o el título (ignorando mayúsculas/minúsculas)
   List<Books> findByAuthorContainingIgnoreCaseOrTitleContainingIgnoreCase(String text, String text2);
    
   List<Books> findByStatus(State state);
}
