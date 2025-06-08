package co.com.andres.services;

import java.util.List;

import co.com.andres.models.dto.LoanResponse;
import co.com.andres.models.entities.Loans;

public interface LoanServices {
    
    // odtine todos los prestamos 
    List<LoanResponse> getAllLoan();

    // borra pretamo por id
    void deleteLoan(Long idLoan);

    //crea un pretamo con el id de usuario y el id de libro
    Loans createLoanWithUserAndBook(Long userId, Long bookId);

    // devuelve el pretamo con el id del pretamo
    Loans returnLoan(Long loanId);

}
