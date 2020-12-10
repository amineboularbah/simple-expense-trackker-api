package com.example.expensetracker.repositories.transactionRepos;

import com.example.expensetracker.exceptions.EtBadRequestException;
import com.example.expensetracker.exceptions.EtRessourceNotFoundException;
import com.example.expensetracker.models.Transaction;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionRepository {

    List<Transaction> getAll(int userId, int categoryId);
    Transaction findById(int userId, int categoryId, int transactionId) throws EtRessourceNotFoundException;

    int create(int userId, int categoryId, Double amount,String note, Long transactionDate) throws EtBadRequestException;

    void update(int userId, int categoryId, int transactionId, Transaction transaction) throws EtRessourceNotFoundException;

    void removeById(int userId, int categoryId, int transactionId) throws EtRessourceNotFoundException;
}
