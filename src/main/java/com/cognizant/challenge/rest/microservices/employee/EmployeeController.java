package com.cognizant.challenge.rest.microservices.employee;

import com.cognizant.challenge.rest.microservices.loan.Loan;
import com.cognizant.challenge.rest.microservices.loan.LoanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;


import java.net.URI;
import java.util.List;

@RestController()
@RequestMapping("/employees")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;
    @Autowired
    private LoanService loanService;

    @PostMapping
    public ResponseEntity createEmployee(@RequestBody Employee employee){
        Employee createdEmployee = employeeService.createEmployee(employee);
        URI uri = ServletUriComponentsBuilder
               .fromCurrentRequestUri()
               .path("/{lastName}")
               .buildAndExpand(employee.getLastName())
               .toUri();

       return ResponseEntity.created(uri).build();
    }

    @GetMapping("/{lastName}")
    public List<Employee> getEmployeeByLastName(@PathVariable String lastName){
        return employeeService.findByLastName(lastName).get();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity softDeleteEmployee(@PathVariable int id) {
        Employee softDeletedEmployee =  employeeService.softDelete(id);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{employeeId}/loans")
    public ResponseEntity createLoan(@RequestBody Loan loan, @PathVariable("employeeId") int employeeId){
        Loan createdLoan = loanService.createLoan(loan,employeeId);
        URI uri = ServletUriComponentsBuilder
                .fromCurrentContextPath()
                .path("loans/employee/{employeeId}")
                .buildAndExpand(employeeId)
                .toUri();

        return ResponseEntity.created(uri).build();
    }

}
