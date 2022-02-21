package com.cognizant.challenge.rest.microservices.loan;

import com.cognizant.challenge.rest.microservices.employee.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController()
@RequestMapping("/loans")
public class LoanController {

    @Autowired
    private LoanService loanService;
    @Autowired
    private EmployeeService employeeService;

    @GetMapping("/employee/{id}")
    public List<Loan> getActiveLoansByEmployee(@PathVariable int id){
        return loanService.getActiveLoansByEmployee(id);
    }

    @GetMapping()
    public List<Loan> getAllLoans()
    {
        return loanService.getAllLoans();
    }

    @GetMapping("/employee/{id}/paid")
    public List<Loan> getInactiveLoansByEmployee(@PathVariable int id){
        return loanService.getInactiveLoansByEmployee(id,true);
    }

    @PostMapping("/pay/employee/{id}")
    public ResponseEntity payLoan(@PathVariable int id,@RequestBody Loan loan){
        loanService.payLoan(id,loan);
        URI uri = ServletUriComponentsBuilder
                //.fromCurrentRequest() no
                .fromCurrentContextPath()
                .path("loans/employee/{id}/paid")
                .buildAndExpand(id)
                .toUri();
        return ResponseEntity.created(uri).build();
    }

}
