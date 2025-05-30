package co.com.andres.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import co.com.andres.models.entities.Loans;

public interface LoanRepository extends JpaRepository<Loans, Long >{
    
}
