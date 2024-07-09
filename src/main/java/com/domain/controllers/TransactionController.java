package com.domain.controllers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.domain.helpers.CommonUtil;
import com.domain.models.entities.Transaction;
import com.domain.models.entities.User;
import com.domain.services.TransactionService;

@RestController
@RequestMapping("/api")
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    @PostMapping("/transaction/register")
    public ResponseEntity<?> createTransaction(@RequestBody Transaction transaction) {
        Map<String, String> result = new HashMap<>();
        User user = transactionService.getUserById(transaction.getUserId());
        String nameU = user.getName();
        String typeU = user.getType();
        String literU = user.getLiter();
        String userIdT = transaction.getUserId();
        String literT = transaction.getLiter();
        String typeT = transaction.getType();
        String nameT = transaction.getName();
        Integer literCurrent = Integer.parseInt(literT);
        Integer literAvailable = Integer.parseInt(user.getLiter());

        if (literCurrent > 50 || literCurrent > literAvailable) {
            result.put("message", CommonUtil.getErrMessageInsufience());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result);
        }

        if (!nameT.equalsIgnoreCase(nameU) || !typeT.equalsIgnoreCase(typeU)) {
            result.put("message", CommonUtil.getErrMessageInquiryAccount());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result);
        }

        try {
            CommonUtil.updateLiter(transactionService, literT, literU, literT, userIdT);
            transactionService.registerTransaction(transaction);
            result.put("message", CommonUtil.getSuccesMessage(typeU, nameU));
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            result.put("message", CommonUtil.getErrOtherMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(result);
        }
    }

    @GetMapping("/transactions")
    public ResponseEntity<?> getListTransactionById() {
        Map<String, String> result;
        List<Map<String, String>> lsTransaction = new ArrayList<>();
        try {
            List<Transaction> transactions = transactionService.getListTransaction();
            for (Transaction transaction : transactions) {
                String nameT = transaction.getName();
                String typeT = transaction.getType();
                String literT = transaction.getLiter();
                result = new HashMap<>();
                result.put("transaction", CommonUtil.getSuccesTransaction(typeT, nameT, literT));
                lsTransaction.add(result);
            }
            return ResponseEntity.ok(lsTransaction);
        } catch (Exception e) {
            Map<String, String> errorResult = new HashMap<>();
            errorResult.put("message", CommonUtil.getErrOtherMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResult);
        }
    }

    @PostMapping("/transactions/history")
    public ResponseEntity<?> getListTransactionHistory(@RequestBody Map<String, String> request) {
        Map<String, String> result = new HashMap<>();
        Map<String, Object> totalResult = new HashMap<>();
        Map<String, Integer> transactionMap = new HashMap<>();
        List<Map<String, String>> lsTransaction = new ArrayList<>();
        String type = request.get("type");
        Integer totalLiter = 0;

        if (type == null || type.isEmpty()) {
            result.put("message", "Type is required");
            return ResponseEntity.badRequest().body(result);
        }

        try {
            List<Transaction> transactions = transactionService.getlistTransactionHistory(type);
            for (Transaction transaction : transactions) {
                String nameT = transaction.getName();
                Integer literT = Integer.parseInt(transaction.getLiter());
                transactionMap.put(nameT, transactionMap.getOrDefault(nameT, 0) + literT);
            }

            for (Map.Entry<String, Integer> entry : transactionMap.entrySet()) {
                String name = entry.getKey();
                Integer liter = entry.getValue();
                result = new HashMap<>();
                result.put("transaction", CommonUtil.getSuccessTransactionHistory(name, String.valueOf(liter)));
                lsTransaction.add(result);
                totalLiter += liter;
            }
            totalResult.put("transactions", lsTransaction);
            totalResult.put("total", totalLiter.toString());
            return ResponseEntity.ok(totalResult);
        } catch (Exception e) {
            result.put("message", CommonUtil.getErrOtherMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(result);
        }
    }

}
