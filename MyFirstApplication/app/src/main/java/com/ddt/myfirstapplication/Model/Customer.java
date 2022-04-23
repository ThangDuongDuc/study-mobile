package com.ddt.myfirstapplication.Model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode(callSuper=false)
public class Customer extends Person{
    private String Name;
    private String Address;

    public Customer(String Id, String Email, String Phone, String name, String address) {
        super(Id, Email, Phone);
        Name = name;
        Address = address;
    }
}
