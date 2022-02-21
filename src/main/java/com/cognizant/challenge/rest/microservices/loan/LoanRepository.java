package com.cognizant.challenge.rest.microservices.loan;

import com.cognizant.challenge.rest.microservices.employee.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LoanRepository  extends JpaRepository<Loan,Integer> {

    List<Loan> findLoanByEmployeeId(int id);
    List<Loan> findLoansByEmployeeAndPaid(Employee employee,boolean paid);

}
