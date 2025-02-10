package com.mirasystems.payroll.dbLoader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.mirasystems.payroll.enums.EnumStatus;
import com.mirasystems.payroll.model.Employee;
import com.mirasystems.payroll.model.Order;
import com.mirasystems.payroll.service.EmployeeService;
import com.mirasystems.payroll.service.OrderService;

@Configuration
public class LoadDatabase {

	private static final Logger log = LoggerFactory.getLogger(LoadDatabase.class);
	
	@Bean
	CommandLineRunner initDatabase (EmployeeService employeeService, OrderService orderService) {
		
		return args -> {
			log.info("Preloading " + employeeService.save(new Employee("Michael", "Ribeiro", "Systems Developer Jr")));
			
			employeeService.getAll().forEach(employee -> log.info("Preloaded: ", employee));
			
			orderService.save(new Order("iPhone 13 PRO MAX", EnumStatus.IN_PROGRESS));
			orderService.save(new Order("Samsung Odyssey Monitor", EnumStatus.COMPLETED));
			
			orderService.getAll().forEach(order -> { log.info("Preloaded", order); });
		};
	}
}