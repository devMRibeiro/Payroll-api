package com.mirasystems.payroll.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mirasystems.payroll.model.Employee;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {

}