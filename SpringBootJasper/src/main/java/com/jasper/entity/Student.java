package com.jasper.entity;

import java.sql.Date;

import lombok.Data;

@Data
public class Student {

	private int id;
	private String firstName;
	private String lastName;
	private int age;
	private Date dateOfBirth;
}
