package com.mirasystems.payroll.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mirasystems.payroll.model.Order;

public interface OrderRepository extends JpaRepository<Order, Integer> { }