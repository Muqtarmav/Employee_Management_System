package com.employee.web.exceptions;

public class EmployeeDoesNotExistException extends Throwable{

    public EmployeeDoesNotExistException(String s){
        super(s);
    }
}
