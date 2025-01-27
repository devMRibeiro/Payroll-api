package com.mirasystems.payroll.dbLoader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.mirasystems.payroll.model.Employee;
import com.mirasystems.payroll.repository.EmployeeRepository;

@Configuration
public class LoadDatabase {

	private static final Logger log = LoggerFactory.getLogger(LoadDatabase.class);
	
	@Bean
	CommandLineRunner initDatabase (EmployeeRepository repository) {
		
		return args -> {
			log.info("Preloading " + repository.save(new Employee("Michael", "Ribeiro", "Systems Developer Jr")));
		};
	}
}