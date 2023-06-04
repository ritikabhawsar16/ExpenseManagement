package com.adt.expensemanagement.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.adt.expensemanagement.models.CapExDetails;

@Repository
public interface CapExDetailsRepository extends JpaRepository<CapExDetails, Integer> {

}
