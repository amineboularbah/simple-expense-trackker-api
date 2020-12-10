package com.example.expensetracker.service.transactionService;

import com.example.expensetracker.exceptions.EtBadRequestException;
import com.example.expensetracker.exceptions.EtRessourceNotFoundException;
import com.example.expensetracker.models.Transaction;

import java.util.List;

public interface TransactionService {
    List<Transaction> getAllTransactions(int userId, int categoryId);
    Transaction getTransactionById(int userId, int categoryId, int transactionId) throws EtRessourceNotFoundException;
    Transaction addTransaction(int userId, int categoryId, Double amount, String note, Long transactionDate) throws EtBadRequestException;

    void updateTransaction(int userId, int categoryId, int transactionId, Transaction transaction) throws  EtBadRequestException;

    void removeTransaction(int userId, int categoryId, int transactionId) throws EtRessourceNotFoundException;
}
