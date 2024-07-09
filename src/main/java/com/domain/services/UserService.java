package com.domain.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.domain.models.entities.Transaction;
import com.domain.models.entities.User;
import com.domain.models.repositories.UserRepository;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public void createUser(User user) {
        if (user.getId() == null) {
            user.setId(Transaction.generateId());
        }
        userRepository.insertUser(user.getId(), user.getName(), user.getType(), user.getLiter());
    }

    public void deleteUser(String id) {
        userRepository.deleteUserById(id);
    }

    public User getUser(String id) {
        return userRepository.selectUserById(id);
    }

    public List<User> getListUser() {
        return userRepository.listUser();
    }

    public void updateUser(String name, String type, String liter) {
        userRepository.updateUser(name, type, liter);
    }

    public User getUserByNameAndType(String name, String type) {
        return userRepository.selectNameAndType(name, type);
    }

    public User getBalanceByName(String name) {
        return userRepository.selectLiterByName(name);
    }
}
