package com.ddt.myfirstapplication.Model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class SanPham {
    private String Id;
    private String Name;
    private int isInventory;
}
