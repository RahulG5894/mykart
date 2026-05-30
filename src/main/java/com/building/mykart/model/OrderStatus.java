package com.building.mykart.model;

public enum OrderStatus {
     CREATED("Created"),
     PLACED("Placed"),
     PAID("Paid"),
     SHIPPED("Shipped"),
     DELIVERED("Delivered"),
     CANCELLED("Cancelled");

     public final String name;

    OrderStatus(String name) {
        this.name = name;
    }

}
