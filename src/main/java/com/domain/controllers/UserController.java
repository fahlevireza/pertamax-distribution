package com.domain.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.domain.helpers.CommonUtil;
import com.domain.models.entities.User;
import com.domain.services.UserService;

@RestController
@RequestMapping("/api")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/user/register")
    public ResponseEntity<User> createUser(@RequestBody User user) {
        userService.createUser(user);
        return ResponseEntity.ok(user);
    }

    @DeleteMapping("/delete/user/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable String id) {
        Map<String, String> result = new HashMap<>();
        try {
            userService.deleteUser(id);
            Map<String, String> response = new HashMap<>();
            response.put("message", "User with ID " + id + " has been successfully deleted.");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            result.put("message", CommonUtil.getErrOtherMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(result);
        }
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<?> getUserById(@PathVariable String id) {
        Map<String, String> result = new HashMap<>();
        try {
            User user = userService.getUser(id);

            String name = user.getName();
            String liter = user.getLiter();
            String type = user.getType();

            result.put("message", CommonUtil.getErrMessageInquiryLiter(name, type, liter));
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            result.put("message", CommonUtil.getErrOtherMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(result);
        }
    }

    @GetMapping("/users")
    public ResponseEntity<List<User>> getListUserById() {
        try {
            List<User> users = userService.getListUser();
            if (!users.isEmpty()) {
                return ResponseEntity.ok(users);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping("/user/update")
    public ResponseEntity<?> updateUser(@RequestBody Map<String, String> request) {
        Map<String, Object> result = new HashMap<>();
        try {
            String name = request.get("name");
            String type = request.get("type");
            String liter = request.get("liter");

            if (name == null || type == null || liter == null) {
                result.put("message", "name, type, and liter are required.");
                return ResponseEntity.badRequest().body(result);
            }

            User user = userService.getUserByNameAndType(name, type);
            if (user == null) {
                result.put("message", "nama dan kendaraan belum terdaftar");
                return ResponseEntity.badRequest().body(result);
            }

            userService.updateUser(name, type, liter);
            result.put("message", CommonUtil.getSuccessUpdateUser(name, liter));
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            result.put("message", CommonUtil.getErrOtherMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(result);
        }
    }

    @GetMapping("/user/balance/{name}")
    public ResponseEntity<?> getBalanceByName(@PathVariable String name) {
        Map<String, Object> result = new HashMap<>();
        try {
            User user = userService.getBalanceByName(name);

            if (user.toString() == null) {
                result.put("message", "nama tersebut belum terdaftar.");
                return ResponseEntity.notFound().build();
            }

            String type = user.getType();
            String liter = user.getLiter();
            result.put("message", CommonUtil.getSuccessBalance(type, liter));
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            result.put("message", CommonUtil.getErrOtherMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(result);
        }
    }
}
