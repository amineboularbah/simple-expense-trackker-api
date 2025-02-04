package com.example.expensetracker.api;

import com.example.expensetracker.models.Category;
import com.example.expensetracker.models.Transaction;
import com.example.expensetracker.service.transactionService.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/categories/{categoryId}/transactions")
public class TransactionController {

    @Autowired
    TransactionService transactionService;

    @PostMapping("/new")
    public ResponseEntity<Transaction> addTransaction(HttpServletRequest request,
                                                      @PathVariable("categoryId") int categoryId,
                                                      @RequestBody Map<String, Object> transactionMap){
        int userId = (Integer) request.getAttribute("userId");
        Double amount = Double.valueOf(transactionMap.get("amount").toString());
        String note = (String) transactionMap.get("note");
        Long transactionDate = (Long) transactionMap.get("transactionDate");
        Transaction transaction = transactionService.addTransaction(userId,categoryId,amount,note,transactionDate);

        return new ResponseEntity<>(transaction, HttpStatus.CREATED);
    }

    @GetMapping("/{transactionId}")
    public ResponseEntity<Transaction> getTransactionById(HttpServletRequest request,
                                                           @PathVariable("categoryId") int categoryId,
                                                           @PathVariable("transactionId") int transactionId){
        int userId = (Integer) request.getAttribute("userId");
        Transaction transaction = transactionService.getTransactionById(userId,categoryId,transactionId);
        return new ResponseEntity<>(transaction,HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<List<Transaction>> getAllTransactions(HttpServletRequest request,
                                                          @PathVariable("categoryId") int categoryId
                                                          ){
        int userId = (Integer) request.getAttribute("userId");
        List<Transaction> transaction = transactionService.getAllTransactions(userId,categoryId);
        return new ResponseEntity<>(transaction,HttpStatus.OK);
    }

    @PutMapping("/update/{transactionId}")
    public ResponseEntity<Map<String,Boolean>> updateTransaction(HttpServletRequest request,
                                                                 @PathVariable("transactionId") int transactionId,
                                                                 @PathVariable("categoryId") int categoryId,
                                                                 @RequestBody Transaction transaction){
        int userId = (Integer) request.getAttribute("userId");
        transactionService.updateTransaction(userId,categoryId,transactionId,transaction);
        Map<String, Boolean> map = new HashMap<>();
        map.put("success", true);
        return new ResponseEntity<>(map,HttpStatus.OK);
    }

    @DeleteMapping("/delete/{transactionId}")
    public ResponseEntity<Map<String, Boolean>> deleteTransaction(
            HttpServletRequest request,
            @PathVariable("categoryId") int categoryId,
            @PathVariable("transactionId") int transactionId){
        int userId = (Integer) request.getAttribute("userId");
        transactionService.removeTransaction(userId,categoryId,transactionId);
        Map<String, Boolean> map = new HashMap<>();
        map.put("success", true);
        return new ResponseEntity<>(map,HttpStatus.OK);
    }
}
