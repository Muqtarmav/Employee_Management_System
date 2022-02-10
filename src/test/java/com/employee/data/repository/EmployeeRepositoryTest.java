package com.employee.data.repository;

import com.employee.data.model.Employee;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.AssertionsForClassTypes;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@Sql(scripts = {"/db/insert.sql"})
@SpringBootTest
class EmployeeRepositoryTest {

    @Autowired
    EmployeeRepository employeeRepository;

    @BeforeEach
    void setUp() {
    }

    @Test
    @DisplayName("save an employee ")
    void saveAnEmployeeToDatabaseTest(){

        Employee employee = new Employee();
        employee.setFirstName("Mav");
        employee.setLastName("Adetunji");
        employee.setEmail("mavic@gmail.com");;
        employee.setPhoneNumber("081738392");

        assertThat(employee.getId()).isNull();

        log.info("product saved :: {}", employee );
        employeeRepository.save(employee);
       assertThat(employee.getId()).isNotNull();
       assertThat(employee.getEmail()).isEqualTo("mavic@gmail.com");
       assertThat(employee.getPhoneNumber()).isEqualTo("081738392");

    }

    @Test
    @DisplayName("find all employees in the database")

    void findAllEmployeesTest() {
        List<Employee> employees = employeeRepository.findAll();
        assertThat(employees).isNotNull();
        assertThat(employees.size()).isEqualTo(4);

    }


    @Test
    @DisplayName("find employee by email")

    void findEmployeeByEmailTest(){

        Employee employee =  employeeRepository.findByEmail("Mav@gmail.com").orElse(null);
        assertThat(employee).isNotNull();
        assertThat(employee.getId()).isEqualTo(11);
        assertThat(employee.getFirstName()).isEqualTo("John");
        assertThat(employee.getLastName()).isEqualTo("Ade");
        assertThat(employee.getPhoneNumber()).isEqualTo("08130249216");;

        log.info("employee records retrieved :: {} ", employee);
    }


    @Test
    @DisplayName("update employee records")

    void updateEmployeeRecords() {

        Employee savedEmployee = employeeRepository.findByEmail("Mav@gmail.com").orElse(null);
        assertThat(savedEmployee).isNotNull();

        assertThat(savedEmployee.getEmail()).isEqualTo("Mav@gmail.com");
        assertThat(savedEmployee.getPhoneNumber()).isEqualTo("08130249216");
        savedEmployee.setEmail("muqtar@gmail.com");
        savedEmployee.setPhoneNumber("08130249216");

        employeeRepository.save(savedEmployee);
        assertThat(savedEmployee.getEmail()).isEqualTo("muqtar@gmail.com");
        assertThat(savedEmployee.getPhoneNumber()).isEqualTo("08130249216");

    }

}

