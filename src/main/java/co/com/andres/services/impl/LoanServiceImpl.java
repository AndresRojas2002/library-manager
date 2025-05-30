package co.com.andres.services.impl;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.stereotype.Service;

import co.com.andres.exceptions.BooksNotFoundException;
import co.com.andres.exceptions.LoansNotFoundException;
import co.com.andres.exceptions.UserNotFoundExeption;
import co.com.andres.models.dto.LoanRequest;
import co.com.andres.models.dto.LoanResponse;
import co.com.andres.models.entities.Books;
import co.com.andres.models.entities.Loans;
import co.com.andres.models.entities.StateBook;
import co.com.andres.models.entities.StateLoan;
import co.com.andres.models.entities.StateUser;
import co.com.andres.models.entities.Users;
import co.com.andres.repositories.BookRepository;
import co.com.andres.repositories.LoanRepository;
import co.com.andres.repositories.UserRepository;
import co.com.andres.services.LoanServices;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class LoanServiceImpl implements LoanServices {

    private final LoanRepository loanRepository;
    private final UserRepository userRepository;
    private final BookRepository bookRepository;
    private DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE;

    @Override
    // odtine todos los prestamos
    public List<LoanResponse> getAllLoan() {
        return loanRepository.findAll().stream()
                .map(this::toResponse)
                .toList();
    }

    @Override
    // borra un prestamo
    public LoanResponse deleteLoan(Long idLoan) {
        var opcionalLoan = loanRepository.findById(idLoan);
        if (!opcionalLoan.isPresent()) {
            throw new LoansNotFoundException("EL PRESTAMO CON ESE ID NO EXISTE");

        }
        var loan = opcionalLoan.get();
        loanRepository.delete(loan);
        return toResponse(loan);

    }

    @Override
    @Transactional
    public Loans createLoanWithUserAndBook(Long userId, Long bookId) {

        Users user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundExeption("EL USUARIO CON ESE ID NO EXISTE"));

        Books book = bookRepository.findById(bookId)
                .orElseThrow(() -> new BooksNotFoundException("EL LIBRO CON ESE ID NO EXISTE"));

        if (book.getState() != StateBook.AVAILABLE) {
            throw new BooksNotFoundException("NO SE PUEDE PRESTAR EL LIBRO: YA SE ENCUENTRA PRESTADO");
        }
        if (user.getStateUser() != StateUser.WITHOUT_LOAN) {
            throw new UserNotFoundExeption("NO SE PUEDE PRESTAR EL LIBRO: EL USUARIO YA TIENE UN LIBRO PRESTADO");
        }

        Loans loan = new Loans();
        loan.setIdUser(user);
        loan.setIdBook(book);
        loan.setLoanDate(LocalDate.now());
        loan.setStateLoan(StateLoan.ACTIVE);

        book.setState(StateBook.LOANED);
        user.setStateUser(StateUser.WITH_LOAN);

        return loanRepository.save(loan);
    }

    @Override
    @Transactional
    public Loans returnLoan(Long loanId) {

        Loans loan = loanRepository.findById(loanId)
                .orElseThrow(() -> new LoansNotFoundException("NO SE ENCONTRÓ UN PRÉSTAMO CON ESE ID"));

        Users user = loan.getIdUser();
        Books book = loan.getIdBook();

        if (loan.getStateLoan() != StateLoan.ACTIVE) {
            throw new LoansNotFoundException("EL PRÉSTAMO YA FUE DEVUELTO O NO ESTÁ ACTIVO");
        }

        loan.setStateLoan(StateLoan.NOT_ACTIVE);
        book.setState(StateBook.AVAILABLE);
        user.setStateUser(StateUser.WITHOUT_LOAN);

        return loanRepository.save(loan);
    }

    private LoanResponse toResponse(Loans loans) {
        var response = new LoanResponse();
        response.setIdLoan(loans.getIdLoan());
        response.setIdUser(loans.getIdUser());
        response.setIdBook(loans.getIdBook());
        response.setLoanDate(loans.getLoanDate().format(formatter));
        response.setStateLoan(loans.getStateLoan().toString());
        return response;
    }

    private Loans toEntity(LoanRequest loanRequest) {
        var entity = new Loans();
        entity.setIdBook(loanRequest.getIdBook());
        entity.setIdUser(loanRequest.getIdUser());
        entity.setLoanDate(LocalDate.now());
        entity.setStateLoan(StateLoan.ACTIVE);
        return entity;
    }
}
