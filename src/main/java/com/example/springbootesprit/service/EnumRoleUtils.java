package com.example.springbootesprit.service;

import com.example.springbootesprit.entities.EnumRole;

import java.util.Optional;
import java.util.stream.Stream;

public class EnumRoleUtils {
    public static Optional<EnumRole> findByName(String name) {
        return Stream.of(EnumRole.values())
                .filter(role -> role.name().equals(name))
                .findFirst();
    }
}
