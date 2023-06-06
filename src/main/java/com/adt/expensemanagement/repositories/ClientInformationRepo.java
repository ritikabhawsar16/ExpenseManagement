package com.adt.expensemanagement.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.adt.expensemanagement.models.ClientInformation;

public interface ClientInformationRepo extends JpaRepository<ClientInformation, Integer>{

}
