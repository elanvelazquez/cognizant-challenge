package com.cognizant.challenge.rest.microservices.employee;

import com.cognizant.challenge.rest.microservices.exception.GenericRequestException;
import com.cognizant.challenge.rest.microservices.loan.LoanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private LoanRepository loanRepository;

    public Employee createEmployee(Employee employee) {
        if(employeeRepository.findByLastNameAndFirstName(employee.getLastName(),employee.getFirstName()).get().stream().count()>0)
        {
            throw new GenericRequestException("User already created, please try again.",HttpStatus.CONFLICT);
        }
        Employee savedEmployee =  employeeRepository.save(employee);
        return savedEmployee;
    }

    public Optional<List<Employee>> findByLastName(String lastName) {
        Optional<List<Employee>> employee = employeeRepository.findByLastName(lastName);
        if(employee.isEmpty()) throw new GenericRequestException("Employee not found, please try again.",HttpStatus.NOT_FOUND);
        return employee;
    }

    public Employee softDelete(int employeeId)
    {
        Optional<Employee> employeeToDelete = null;
            employeeToDelete = employeeRepository.findById(employeeId);
            if(employeeToDelete.isEmpty()) throw new GenericRequestException("Employee not exists, please try again.",HttpStatus.NOT_FOUND);
            if(loanRepository.findLoanByEmployeeId(employeeToDelete.get().getId()).stream().count() >0)
                throw new GenericRequestException("Employee can not be deleted because there are active loans.",HttpStatus.BAD_REQUEST);

            employeeToDelete.get().setDeleted(true);
            employeeRepository.save(employeeToDelete.get());

        return employeeToDelete.get();
    }

}
