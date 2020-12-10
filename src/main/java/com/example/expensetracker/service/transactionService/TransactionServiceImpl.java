package com.example.expensetracker.service.transactionService;

import com.example.expensetracker.exceptions.EtBadRequestException;
import com.example.expensetracker.exceptions.EtRessourceNotFoundException;
import com.example.expensetracker.models.Transaction;
import com.example.expensetracker.repositories.transactionRepos.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class TransactionServiceImpl implements TransactionService{

    @Autowired
    TransactionRepository transactionRepository;

    @Override
    public List<Transaction> getAllTransactions(int userId, int categoryId) {
        return transactionRepository.getAll(userId, categoryId);
    }

    @Override
    public Transaction getTransactionById(int userId, int categoryId, int transactionId) throws EtRessourceNotFoundException {
        return transactionRepository.findById(userId,categoryId,transactionId);
    }

    @Override
    public Transaction addTransaction(int userId, int categoryId, Double amount, String note, Long transactionDate) throws EtBadRequestException {
        int transactionId = transactionRepository.create(userId, categoryId, amount,note, transactionDate);
        return transactionRepository.findById(userId,categoryId,transactionId);
    }

    @Override
    public void updateTransaction(int userId, int categoryId, int transactionId, Transaction transaction) throws EtBadRequestException {
        transactionRepository.update(userId,categoryId,transactionId,transaction);
    }

    @Override
    public void removeTransaction(int userId, int categoryId, int transactionId) throws EtRessourceNotFoundException {
        transactionRepository.removeById(userId,categoryId,transactionId);
    }
}
