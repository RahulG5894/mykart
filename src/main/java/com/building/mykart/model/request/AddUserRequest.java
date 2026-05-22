package com.building.mykart.model.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AddUserRequest {
    private String name;
    private String userName;
    private String password;
    private String type;
    private String address;
}
