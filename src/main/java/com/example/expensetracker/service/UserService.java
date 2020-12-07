package com.example.expensetracker.service;

import com.example.expensetracker.exceptions.EtAuthException;
import com.example.expensetracker.models.User;
import org.springframework.stereotype.Service;

@Service
public interface UserService {
    User validateUser(String email, String password) throws EtAuthException;
    User registerUser(String firstName,String lastName, String email, String password) throws EtAuthException;
}
