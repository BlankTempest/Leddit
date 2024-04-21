package com.example.leddit.Model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

import org.aspectj.internal.lang.annotation.ajcITD;

@Entity
@Table(name = "Users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(name = "created_date", nullable = false)
    private LocalDateTime createdDate = LocalDateTime.now();

    @Column(name = "admin", nullable = false)
    private int admin = 0;

    @Column(name = "active", nullable = false)
    private int active = 1;

    public int getAdmin() {
        return admin;
    }

    public void setAdmin(int a) {
        admin = a;
    }

    public int getActive() {
        return active;
    }

    public void setActive(int a) {
        active = a;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }
}
