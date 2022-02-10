package com.employee.service;

import com.employee.data.dtos.EmployeeDto;
import com.employee.data.model.Employee;
import com.employee.data.repository.EmployeeRepository;
import com.employee.web.exceptions.EmployeeDoesNotExistException;
import com.employee.web.exceptions.EmployeeLogicException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    EmployeeRepository employeeRepository;

    @Override
    public List<Employee> getAllEmployee() {

        return employeeRepository.findAll();
    }

    @Override
    public Employee addEmployee(EmployeeDto employeeDto) throws EmployeeLogicException {

        if (employeeDto == null){
            throw new IllegalArgumentException("Argument cannot be null");
        }

        Optional<Employee> query = employeeRepository.findByEmail(employeeDto.getEmail());
        if (query.isPresent()){
            throw new EmployeeLogicException("employee email exists" +employeeDto.getEmail());
        }

        Employee employee = new Employee();

        employee.setFirstName(employeeDto.getFirstName());
        employee.setLastName(employeeDto.getLastName());
        employee.setEmail(employeeDto.getEmail());
        employee.setPhoneNumber(employeeDto.getPhoneNumber());

      return  employeeRepository.save(employee);

    }

    private Employee saveOrUpdate(Employee employee) throws EmployeeLogicException {
        if (employee == null) {
            throw new EmployeeLogicException("employee cannot be null");
        }
        return employeeRepository.save(employee);

    }


    @Override
    public Employee updateEmployeeRecords(Long id, JsonPatch employeePatch) throws EmployeeLogicException {
        Optional<Employee> employeeQuery = employeeRepository.findById(id);

        if (employeeQuery.isEmpty()) {
            throw new EmployeeLogicException("employee with id" + id + "does not exist");
        }
        Employee theEmployee = employeeQuery.get();
        try {
            theEmployee = applyPatchToEmployee(employeePatch, theEmployee);
            return saveOrUpdate(theEmployee);
        }
        catch (JsonPatchException | JsonProcessingException | EmployeeLogicException je){
        throw new EmployeeLogicException("update failed");
    }
    }


    private Employee applyPatchToEmployee(JsonPatch productPatch, Employee theEmployee) throws EmployeeLogicException, JsonPatchException, JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode patched = productPatch.apply(objectMapper.convertValue(theEmployee, JsonNode.class));

        return objectMapper.treeToValue(patched, Employee.class);
    }



    @Override
    public Employee findEmployeeByEmail(String email) throws EmployeeDoesNotExistException {

        if ( email == null){
            throw new IllegalArgumentException("argument cannot be null");
        }

        Optional<Employee> queryResult = employeeRepository.findByEmail(email);
        if (queryResult.isPresent()) {
            return queryResult.get();
        }
            throw new EmployeeDoesNotExistException("employee with this email :"  + email + "does not exist");
        }


    @Override
    public void delete(Employee employee) {
        employeeRepository.delete(employee);

    }



}
