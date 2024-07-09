package com.domain.models.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import com.domain.models.entities.User;

public interface UserRepository extends JpaRepository<User, String> {

    @Modifying
    @Transactional
    @Query(value = "INSERT INTO user (id, name, type, liter) VALUES (?1, ?2, ?3, ?4)", nativeQuery = true)
    void insertUser(String id, String name, String type, String liter);

    @Query(value = "SELECT * FROM user", nativeQuery = true)
    User selectUser();

    @Query(value = "SELECT * FROM user WHERE id = ?1", nativeQuery = true)
    User selectUserById(String id);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM user WHERE id = ?1", nativeQuery = true)
    int deleteUserById(String id);

    @Query(value = "SELECT * FROM user", nativeQuery = true)
    List<User> listUser();

    @Modifying
    @Transactional
    @Query(value = "UPDATE user SET liter = liter + CAST(?3 AS SIGNED) WHERE name = ?1 AND type = ?2", nativeQuery = true)
    void updateUser(String name, String type, String liter);

    @Query(value = "SELECT * FROM user WHERE name = ?1 AND type = ?2", nativeQuery = true)
    User selectNameAndType(String name, String type);

    @Query(value = "SELECT * FROM user WHERE name = ?1", nativeQuery = true)
    User selectLiterByName(String name);
}
