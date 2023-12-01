package com.example.springbootesprit.controller;


import com.example.springbootesprit.entities.User;
import com.example.springbootesprit.service.IUserService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/api/v1")

public class UserController {
    @Autowired
    IUserService iUserService;

    @GetMapping("/allUsers")
    List<User> allUsers() {
        return iUserService.getUser();
    }

    @GetMapping("/User/{id}")
    User UserById(@PathVariable Integer id) {
        return iUserService.getUserById(id);
    }

    @PutMapping("/updateUser")
    User update(@RequestBody User user) {
        return iUserService.update(user);
    }

    @PutMapping("/updateUser/{id}")
    User updateUserById(@RequestBody User user) {
        return iUserService.update(user);
    }
    @DeleteMapping("/deleteUser/{id}")
    void deleteUser(@PathVariable Integer id)
    {
        iUserService.delete(id);
    }
    @DeleteMapping("/deleteUser")
    void deleteUser(@RequestBody User user) {
        iUserService.deleteUser(user);
    }
    @GetMapping({"/listeUserExcel"})
    public void getlistUserExcel() {
        this.iUserService.getlistUserExcel();
    }
    @GetMapping("/carteetudiant/{id}")
    public String generecarteetudpdf (@PathVariable("id") Integer id){
        return iUserService.genereCarteUserPdf(id) ;

    }
}

