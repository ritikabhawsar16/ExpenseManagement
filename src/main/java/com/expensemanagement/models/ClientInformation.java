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
<<<<<<< HEAD
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
=======
@Table(name = "client_information", catalog = "EmployeeDB", schema = "expense_schema")
public class ClientInformation {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(columnDefinition = "serial")
	private int id;

	@Column(name = "address", nullable = false)
	private String address;

	@Column(name = "company_name", nullable = false)
	private String companyName;

	@Column(name = "contact_person", nullable = false)
	private String contactPerson;

	@Column(name = "email", nullable = false)
	private String email;

	@Column(name = "gstin", nullable = false)
	private String gstin;

	@Column(name = "phone", nullable = false)
	private int phone;

}
>>>>>>> 42c5eaae09df00cf9d81c1d50684871d64b07cc7
