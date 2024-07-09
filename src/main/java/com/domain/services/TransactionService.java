package com.domain.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.domain.models.entities.Transaction;
import com.domain.models.entities.User;
import com.domain.models.repositories.TransactionRepository;
import com.domain.models.repositories.UserRepository;

@Service
public class TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private UserRepository userRepository;

    public void registerTransaction(Transaction transaction) {
        if (transaction.getTransactionId() == null) {
            transaction.setTransactionId(Transaction.generateId());
        }
        if (transaction.getDate() == null) {
            transaction.setDate(Transaction.getCurrentDateTime());
        }
        transactionRepository.insertTransaction(transaction.getTransactionId(), transaction.getDate(),
                transaction.getUserId(), transaction.getLiter());
    }

    public void updateTransaction(String liter, String id) {
        transactionRepository.updateTransaction(liter, id);
    }

    public User getUserById(String id) {
        return userRepository.selectUserById(id);
    }

    public List<Transaction> getListTransaction() {
        return transactionRepository.listTransaction();
    }

    public List<Transaction> getlistTransactionHistory(String type) {
        return transactionRepository.listTransactionHistory(type);
    }
}
