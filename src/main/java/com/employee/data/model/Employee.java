package com.employee.data.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class Employee {

    @Id
    @GeneratedValue(strategy =  GenerationType.AUTO)
    private Long id;

    private String firstName;
    @Column(unique = true, nullable = false)
    private String lastName;
    private String email;
    private String phoneNumber;
 //   private String socialSecurityNo;

}
