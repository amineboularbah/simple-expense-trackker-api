package com.example.expensetracker.repositories;

import com.example.expensetracker.exceptions.EtAuthException;
import com.example.expensetracker.models.User;


public interface UserRepository {
    int create(String firstName, String lastName, String email, String password) throws EtAuthException;
    User findByEmailAndPassword(String email,String password) throws EtAuthException;

    int getCountByEmail(String email);

    User findById(int userId);
}
