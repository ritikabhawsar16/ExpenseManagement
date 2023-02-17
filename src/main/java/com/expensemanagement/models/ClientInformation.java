package com.expensemanagement.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(catalog = "EmployeeDB", schema = "expense_schema",name="client_information")
public class ClientInformation {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(columnDefinition = "serial")
	private int id;
	private String companyName;
	private String address;
	private Long phone;
	private String email;
	private String contactPerson;
	private String gstin;
}
