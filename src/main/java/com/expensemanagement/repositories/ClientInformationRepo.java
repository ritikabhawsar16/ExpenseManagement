package com.expensemanagement.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.expensemanagement.models.ClientInformation;

public interface ClientInformationRepo extends JpaRepository<ClientInformation, Integer>{

}
