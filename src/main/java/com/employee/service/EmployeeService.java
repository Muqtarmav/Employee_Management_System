package com.employee.service;

import com.employee.data.dtos.EmployeeDto;
import com.employee.data.model.Employee;
import com.employee.web.exceptions.EmployeeDoesNotExistException;
import com.employee.web.exceptions.EmployeeLogicException;
import com.github.fge.jsonpatch.JsonPatch;

import java.util.List;

public interface EmployeeService {

    List<Employee> getAllEmployee();
    Employee addEmployee(EmployeeDto employeeDto) throws EmployeeLogicException;
    Employee updateEmployeeRecords(Long id, JsonPatch employee) throws EmployeeLogicException;
    Employee findEmployeeByEmail(String email) throws EmployeeLogicException, EmployeeDoesNotExistException;
    void delete(Employee employee) throws EmployeeLogicException;


}
