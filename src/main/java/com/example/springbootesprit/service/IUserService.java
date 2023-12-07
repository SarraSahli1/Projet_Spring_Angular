package com.example.springbootesprit.service;

import com.example.springbootesprit.entities.Reservation;
import com.example.springbootesprit.entities.User;

import java.util.List;
import java.util.Optional;

public interface IUserService {
    List<User> getUser();
    Optional<User> findById(Integer id);
    User update(User user);
    User updateById(User user);
    void deleteUser(User user);

    void delete(Integer id);
    User getUserById(Integer id);
    Optional<User> findByEmail(String email);

    void createPasswordResetTokenForUser(User user, String passwordToken);

    String validatePasswordResetToken(String passwordResetToken);

    User findUserByPasswordToken(String passwordResetToken);

    void resetUserPassword(User user, String newPassword);
    void getlistUserExcel();

    String genereCarteUserPdf(Integer id);

    User addUser(User user);
}
