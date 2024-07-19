package com.adt.expensemanagement.repositories;

import com.adt.expensemanagement.models.User;
import org.springframework.data.jpa.repository.JpaRepository;


public interface UserRepo extends JpaRepository<User, Integer> {

}