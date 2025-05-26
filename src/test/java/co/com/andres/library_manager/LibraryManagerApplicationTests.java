package co.com.andres.library_manager;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import co.com.andres.controllers.BookController;
import co.com.andres.services.BookServices;

@SpringBootTest
class LibraryManagerApplicationTests {

	@Autowired
	private BookController bookController;

	@Autowired
	private BookServices bookServices;

	@Test
	void contextLoads() {
		// Verifica que el contexto de Spring se cargue correctamente
		assertNotNull(bookController);
		assertNotNull(bookServices);
	}

	@Test
	void testBookControllerNotNull() {
		// Verifica que el controlador no sea nulo
		assertNotNull(bookController, "El controlador de libros no debería ser nulo");
	}

	@Test
	void testBookServicesNotNull() {
		// Verifica que el servicio no sea nulo
		assertNotNull(bookServices, "El servicio de libros no debería ser nulo");
	}
}
