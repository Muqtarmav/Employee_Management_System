package com.employee.web.controllers;

import com.employee.data.dtos.EmployeeDto;
import com.employee.data.model.Employee;
import com.employee.service.EmployeeService;
import com.employee.web.exceptions.EmployeeDoesNotExistException;
import com.employee.web.exceptions.EmployeeLogicException;
import com.github.fge.jsonpatch.JsonPatch;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.web.JsonPath;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/employee")
public class EmployeeRestController {

    @Autowired
    EmployeeService employeeService;

    @GetMapping()
    public ResponseEntity<?> getAllEmployees() {

        List<Employee> employeeList = employeeService.getAllEmployee();
        return ResponseEntity.ok().body(employeeList);
    }


    @PostMapping()
    public ResponseEntity<?> addEmployees(@RequestBody EmployeeDto employeeDto) {


        log.info("added an employee :: {} ", employeeDto);
        try {
            Employee savedEmployee = employeeService.addEmployee(employeeDto);
            return ResponseEntity.ok().body(savedEmployee);
        } catch (EmployeeLogicException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


    @GetMapping("/api/get-employee-email")
    public ResponseEntity<?> findEmployeeByEmail(String email) {
        try {
            Employee savedEmployee = employeeService.findEmployeeByEmail(email);
            return ResponseEntity.ok().body(savedEmployee);
        } catch (EmployeeDoesNotExistException | EmployeeLogicException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PatchMapping(path = "{id}", consumes = "application/json-patch+json")
    public ResponseEntity<?> updateEmployeeRecords(@PathVariable Long id, @RequestBody JsonPatch employeePatch){

        try {
            Employee updatedEmployee = employeeService.updateEmployeeRecords(id, employeePatch);
            log.info("updated employee {}", updatedEmployee);
            return ResponseEntity.status(HttpStatus.OK).body(updatedEmployee);
        }

        catch (EmployeeLogicException e){
            ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
        return  null;

        }

    }

