package com.adt.expensemanagement.repositories;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.adt.expensemanagement.models.CapExDetails;

@Repository
public interface CapExDetailsRepository extends JpaRepository<CapExDetails, Integer> {

}
