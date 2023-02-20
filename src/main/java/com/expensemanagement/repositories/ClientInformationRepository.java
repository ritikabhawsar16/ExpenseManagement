package com.expensemanagement.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.expensemanagement.models.ClientInformation;

@Repository
public interface ClientInformationRepository  extends JpaRepository<ClientInformation, Integer> {

}
