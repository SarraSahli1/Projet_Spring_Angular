package com.example.springbootesprit.repositories;

import com.example.springbootesprit.config.PasswordResetToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PasswordResetTokenRepository extends JpaRepository<PasswordResetToken,Long> {

    PasswordResetToken findByToken(String passwordResetToken);
}
