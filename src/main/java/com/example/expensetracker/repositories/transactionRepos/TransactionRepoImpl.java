package com.example.expensetracker.repositories.transactionRepos;

import com.example.expensetracker.exceptions.EtBadRequestException;
import com.example.expensetracker.exceptions.EtRessourceNotFoundException;
import com.example.expensetracker.models.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;

@Repository
public class TransactionRepoImpl implements TransactionRepository {


    private static final String SQL_FIND_ALL = "SELECT TRANSACTION_ID, USER_ID, CATEGORY_ID, AMOUNT, NOTE, TRANSACTION_DATE FROM ET_TRANSACTIONS WHERE USER_ID = ? AND CATEGORY_ID = ?";
    private static final String SQL_FIND_BY_ID = "SELECT TRANSACTION_ID, CATEGORY_ID, USER_ID, AMOUNT, NOTE, TRANSACTION_DATE FROM ET_TRANSACTIONS WHERE USER_ID = ? AND CATEGORY_ID = ? AND TRANSACTION_ID = ?";
    private static final String SQL_CREATE = "INSERT INTO ET_TRANSACTIONS (TRANSACTION_ID, USER_ID, CATEGORY_ID, AMOUNT, NOTE, TRANSACTION_DATE) VALUES(NEXTVAL('ET_TRANSACTIONS_SEQ'), ?, ?, ?, ?, ?)";
    private static final String SQL_UPDATE = "UPDATE ET_TRANSACTIONS SET AMOUNT = ?, NOTE = ?, TRANSACTION_DATE = ? WHERE USER_ID = ? AND CATEGORY_ID = ? AND TRANSACTION_ID = ?";
    private static final String SQL_DELETE = "DELETE FROM ET_TRANSACTIONS WHERE USER_ID = ? AND CATEGORY_ID = ? AND TRANSACTION_ID = ?";

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Override
    public List<Transaction> getAll(int userId, int categoryId) {
        try {
            return jdbcTemplate.query(SQL_FIND_ALL, new Object[]{userId,categoryId}, transactionRowMapper);
        }catch (Exception e) {
            throw new EtBadRequestException(e.toString());
        }
    }

    @Override
    public Transaction findById(int userId, int categoryId, int transactionId) throws EtRessourceNotFoundException {
        try{
            return jdbcTemplate.queryForObject(SQL_FIND_BY_ID, new Object[]{userId,categoryId,transactionId}, transactionRowMapper);
        }catch (Exception e){
            throw new EtRessourceNotFoundException("Transaction not found");
        }
    }

    @Override
    public int create(int userId, int categoryId, Double amount,String note, Long transactionDate) throws EtBadRequestException {
        try {
            KeyHolder keyHolder = new GeneratedKeyHolder();
            jdbcTemplate.update(connection -> {
                PreparedStatement ps = connection.prepareStatement(SQL_CREATE, Statement.RETURN_GENERATED_KEYS);
                ps.setInt(1, userId);
                ps.setInt(2, categoryId);
                ps.setDouble(3, amount);
                ps.setString(4, note);
                ps.setLong(5, transactionDate);
                return ps;
            }, keyHolder);
            return (Integer) keyHolder.getKeys().get("TRANSACTION_ID");
        }catch (Exception e) {
            throw new EtBadRequestException(e.toString());
        }
    }

    @Override
    public void update(int userId, int categoryId, int transactionId, Transaction transaction) throws EtRessourceNotFoundException {
        try {
            jdbcTemplate.update(SQL_UPDATE, transaction.getAmount(),transaction.getNote(),transaction.getTransactionDate(),userId,categoryId,transactionId);
        }catch (Exception e) {
            throw new EtBadRequestException(e.toString());
        }
    }

    @Override
    public void removeById(int userId, int categoryId, int transactionId) throws EtRessourceNotFoundException {

        int count = jdbcTemplate.update(SQL_DELETE, userId,categoryId,transactionId);
        if(count == 0)
            throw new EtRessourceNotFoundException("Transaction Not Found");
    }

    private final RowMapper<Transaction> transactionRowMapper = ((rs, rowNum) -> new Transaction(
            rs.getInt("TRANSACTION_ID"),
            rs.getInt("USER_ID"),
            rs.getInt("CATEGORY_ID"),
            rs.getDouble("AMOUNT"),
            rs.getString("NOTE"),
            rs.getLong("TRANSACTION_DATE"))
            );
}
