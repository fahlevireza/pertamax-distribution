package com.domain.models.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import java.security.SecureRandom;

@Entity
public class User {
    @Id
    private String id;

    private String name;
    private String type;
    private String liter;

    private static final SecureRandom random = new SecureRandom();

    public static String generateId() {
        long number = Math.abs(random.nextLong());
        return String.format("%016d", number).substring(0, 16);
    }

    @PrePersist
    protected void onCreate() {
        if (this.id == null) {
            this.id = generateId();
        }
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getLiter() {
        return liter;
    }

    public void setLiter(String liter) {
        this.liter = liter;
    }
}
