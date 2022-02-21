package com.cognizant.challenge.rest.microservices.loan;

import com.cognizant.challenge.rest.microservices.employee.Employee;
import com.cognizant.challenge.rest.microservices.employee.EmployeeRepository;
import com.cognizant.challenge.rest.microservices.exception.GenericRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LoanService{

    @Autowired
    private LoanRepository loanRepository;
    @Autowired
    private EmployeeRepository employeeRepository;

    public Loan createLoan(Loan loan, int employeeId){
        Optional<Employee> findEmployee = employeeRepository.findById(employeeId);
        if( findEmployee.isEmpty())
        {
            throw new GenericRequestException("Employee not found",HttpStatus.NOT_FOUND);
        }
        if(loanRepository.findLoanByEmployeeId(findEmployee.get().getId()).stream().count() ==2)
        {
            throw new GenericRequestException("Error trying to create loan, employee can have maximum 2 loans at the same time", HttpStatus.CONFLICT);
        }

        loan.setEmployee(findEmployee.get());
        Loan createdLoan = loanRepository.save(loan);

        return createdLoan;
    }


    public List<Loan> getActiveLoansByEmployee(int id) {
        return loanRepository.findLoanByEmployeeId(id);
    }

    public List<Loan> getAllLoans() {
        return loanRepository.findAll();
    }

    public List<Loan> getInactiveLoansByEmployee(int employeeId,boolean paid){
        Optional<Employee> findEmployee = employeeRepository.findById(employeeId);
        if(findEmployee.isEmpty()) throw new GenericRequestException("Employee not found, please try again.",HttpStatus.NOT_FOUND);

        return loanRepository.findLoansByEmployeeAndPaid(findEmployee.get(),paid);
    }

    public Loan payLoan(int employeeId,Loan loan){
        Optional<Employee> findEmployee = employeeRepository.findById(employeeId);

        if(findEmployee.isEmpty()) throw new GenericRequestException("Employee not found, please try again.",HttpStatus.NOT_FOUND);

        Optional<Loan> findLoan = loanRepository.findById(loan.getId());

        if(findLoan.isEmpty()) throw new GenericRequestException("Loan not found, please try again.",HttpStatus.NOT_FOUND);

        if(loan.getAmount() != findLoan.get().getAmount()) throw new GenericRequestException("The loan must be paid in full.",HttpStatus.CONFLICT);

        findLoan.get().setPaid(true);
        loanRepository.save(findLoan.get());

        return findLoan.get();
    }

}
