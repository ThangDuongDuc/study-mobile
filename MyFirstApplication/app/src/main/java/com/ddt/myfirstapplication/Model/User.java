package com.ddt.myfirstapplication.Model;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class User extends Person{
    public User(String Id, String Email, String Phone) {
        super(Id, Email, Phone);
    }
}
