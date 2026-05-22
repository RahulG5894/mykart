package com.building.mykart.model;

import java.util.Arrays;
import java.util.Optional;

public enum UserType {
    SELLER ("Seller"),
    BUYER ("Buyer");
    private final String name;

    UserType(String name) {
        this.name = name;
    }

    public String getUserType(String type) {
        Optional<UserType> first = Arrays.stream(UserType.values()).filter(i -> i.name.equals(type)).findFirst();
        return first.map(userType -> userType.name).orElse(null);
    }
}
