package com.ddt.readcontacts_contentprovider.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class Contact {
    private String name;
    private String phone;

//    public Contact(String name, String phone) {
//        this.name = name;
//        this.phone = phone;
//    }
//
//    @Override
//    public String toString() {
//        return "" +
//                "name='" + name + '\'' +
//                ", phone='" + phone + '\'';
//    }
}
