package com.domain.models.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.domain.models.entities.Transaction;

public interface TransactionRepository extends JpaRepository<Transaction, String> {

    @Modifying
    @Transactional
    @Query(value = "INSERT INTO transaction (transaction_id, date, user_id, liter) VALUES (?1, ?2, ?3, ?4)", nativeQuery = true)
    void insertTransaction(String transactionId, String date, String userId, String liter);

    @Modifying
    @Transactional
    @Query(value = "UPDATE user SET liter = ?1 WHERE id = ?2", nativeQuery = true)
    void updateTransaction(String liter, String id);

    @Query(value = "SELECT MAX(tr.transaction_id) AS transaction_id, tr.user_id, MAX(tr.date) AS date, us.name, us.type, CAST(SUM(tr.liter) AS UNSIGNED) AS liter FROM transaction tr LEFT JOIN user us ON tr.user_id = us.id GROUP BY tr.user_id, us.name, us.type ORDER BY tr.user_id ASC", nativeQuery = true)
    List<Transaction> listTransaction();

    @Query(value = "SELECT tr.transaction_id, tr.user_id, tr.date, us.name, us.type, tr.liter FROM transaction tr LEFT JOIN user us ON tr.user_id = us.id WHERE us.type = :type ORDER BY tr.date ASC", nativeQuery = true)
    List<Transaction> listTransactionHistory(@Param("type") String type);
}
