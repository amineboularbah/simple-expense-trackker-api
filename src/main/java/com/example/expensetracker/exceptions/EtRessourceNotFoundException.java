package com.example.expensetracker.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class EtRessourceNotFoundException extends RuntimeException {
    public  EtRessourceNotFoundException(String message){
        super(message);
    }
}
