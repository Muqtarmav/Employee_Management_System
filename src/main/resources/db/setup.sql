create database employee_db;

create user 'employee_user'@'localhost' identified by 'pass_1234';
grant all privileges on employee_db. * to 'employee_user'@'localhost';
flush privileges ;

