package com.example.expensetracker.service.userService;

import com.example.expensetracker.exceptions.EtAuthException;
import com.example.expensetracker.models.User;
import com.example.expensetracker.repositories.userRepos.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.regex.Pattern;

@Service
@Transactional
public class UserServiceImp implements UserService {

    @Autowired
    UserRepository userRepository;
    @Override
    public User validateUser(String email, String password) throws EtAuthException {
        if(email != null )
            email = email.toLowerCase();
        return userRepository.findByEmailAndPassword(email,password);
    }

    @Override
    public User registerUser(String firstName, String lastName, String email, String password) throws EtAuthException {
        Pattern pattern = Pattern.compile("^(.+)@(.+)$");
        if(email != null) email = email.toLowerCase();
        if(!pattern.matcher(email).matches())
            throw new EtAuthException("Invalid email format");

        int count = userRepository.getCountByEmail(email);
        if(count>0)
            throw new EtAuthException("Email Already exists");
        int userId = userRepository.create(firstName, lastName, email, password);
        return userRepository.findById(userId);
    }
}
