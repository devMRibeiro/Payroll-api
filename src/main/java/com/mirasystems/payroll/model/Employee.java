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
	

	private String name;
	
	private String role;

	public Employee() { }

	public Employee(String name, String role) {
		this.name = name;
		this.role = role;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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
					 Objects.equals(this.name, employee.name) &&
					 Objects.equals(this.role, employee.role); 
	}

	@Override
	public String toString() {
		return "Employee{id=" + this.id + ", name='" + this.name + '\'' + ", role='" + this.role + '\'' + '}';
  }
}