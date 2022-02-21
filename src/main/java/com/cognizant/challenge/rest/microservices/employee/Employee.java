package com.cognizant.challenge.rest.microservices.employee;

import com.cognizant.challenge.rest.microservices.loan.Loan;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.List;

@Entity
//@SQLDelete(sql = "UPDATE employee SET deleted=true WHERE id=?")
@Where(clause = "deleted = false")
public class Employee {
    @Id
    @GeneratedValue
    private int id;
    private String firstName;
    private String lastName;
    @OneToMany(mappedBy = "employee")
    List<Loan> loans;

    @JsonIgnore
    private boolean deleted = Boolean.FALSE;

    public Employee() {
    }

    public Employee(int id, String firstName, String lastName, boolean isDeleted) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.deleted = isDeleted;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }
}
