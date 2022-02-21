package com.cognizant.challenge.rest.microservices.loan;

import com.cognizant.challenge.rest.microservices.employee.Employee;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

@Entity
public class Loan {
    @Id
    @GeneratedValue
    private int id;
    private double amount;
    private boolean paid = Boolean.FALSE;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="employee_id")
    @JsonIgnore
    private Employee employee;

    public Loan() {
    }

    public Loan(double amount, Employee employee) {
        this.amount = amount;
        this.employee = employee;
    }

    public Loan(int id, double amount, boolean paid, Employee employee) {
        this.id = id;
        this.amount = amount;
        this.paid = paid;
        this.employee = employee;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public boolean isPaid() {
        return paid;
    }

    public void setPaid(boolean paid) {
        this.paid = paid;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }
}
