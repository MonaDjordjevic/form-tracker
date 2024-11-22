package com.example.form_tracker.repository;

import com.example.form_tracker.model.User;
import org.hibernate.id.IntegralDataTypeHolder;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, IntegralDataTypeHolder> {
    Optional<User> findByUsername(String username);
}