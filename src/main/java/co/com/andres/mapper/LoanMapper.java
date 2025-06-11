package co.com.andres.mapper;

import java.time.format.DateTimeFormatter;

import org.springframework.stereotype.Component;

import co.com.andres.models.dto.LoanResponse;
import co.com.andres.models.entities.Loans;

@Component
public class LoanMapper {

    private DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE;

    /**
     * Convierte una entidad Loans a un DTO LoanResponse.
     * 
     * @param loans Entidad a convertir
     * @return LoanResponse con los datos del préstamo
     */
    public LoanResponse toResponse(Loans loans) {
        var response = new LoanResponse();
        response.setIdLoan(loans.getIdLoan());
        response.setIdUser(loans.getIdUser());
        response.setIdBook(loans.getIdBook());
        response.setLoanDate(loans.getLoanDate().format(formatter));
        response.setStateLoan(loans.getStateLoan().toString());
        return response;
    }
    
}
