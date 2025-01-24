package com.mirasystems.payroll.model;

import java.util.Objects;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;

@Entity
public class Employee {

	@Id
  @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "employee_id_sequence")
  @SequenceGenerator(name = "employee_id_sequence", sequenceName = "employee_id_sequence", allocationSize = 1)
  private Integer id;
	private String firstName;
	private String lastName;
	private String role;

	public Employee() { }

	public Employee(String firstName, String lastName, String role) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.role = role;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return this.firstName + " " + this.lastName;
	}

	public void setName(String name) {
		String[] parts = name.split(" ");
		this.firstName = parts[0];
		this.lastName = parts[1];
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

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	@Override
	public boolean equals(Object o) {

		if (this == o)
			return true;

		if (!(o instanceof Employee)) 
			return false;

		Employee employee = (Employee) o;

		return Objects.equals(this.id, employee.id) &&
					 Objects.equals(this.firstName, employee.firstName) &&
					 Objects.equals(this.lastName, employee.lastName) &&
					 Objects.equals(this.role, employee.role); 
	}

	@Override
	public String toString() {
		return "Employee{" + "id=" + this.id + ", firstName='" + this.firstName + '\'' + ", lastName='" + this.lastName + '\'' + ", role='" + this.role + '\'' + '}';
  }
	
	@Override
	public int hashCode() {
		return Objects.hash(this.id, this.firstName, this.lastName, this.role);
	}
}