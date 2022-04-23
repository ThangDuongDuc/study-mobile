package com.ddt.myfirstapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.ddt.myfirstapplication.Custom.CustomArrayAdapter;
import com.ddt.myfirstapplication.Model.Product;
import com.ddt.myfirstapplication.Model.Student;

import java.util.ArrayList;

public class ListViewActivity extends AppCompatActivity {
    ListView listView;
    ArrayAdapter arrayAdapter;
    CustomArrayAdapter customArrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_view);

        listView = findViewById(R.id.listView);

//        String[] arrayList = {"dt1", "dt2", "dt3", "dt4", "dt5"};
        ArrayList<Student> studentArrayList = new ArrayList<Student>();
        studentArrayList.add(new Student("1", "Thang1", 10));
        studentArrayList.add(new Student("2", "Thang2", 8));
        studentArrayList.add(new Student("3", "Thang3", 9));
        studentArrayList.add(new Student("4", "Thang4", 7));
        studentArrayList.add(new Student("5", "Thang5", 0));
        studentArrayList.add(new Student("6", "Thang6", 2));

        ArrayList<Product> productArrayList = new ArrayList<Product>();
        productArrayList.add(new Product(R.drawable.desktop, "2", 1000));
        productArrayList.add(new Product(R.drawable.desktop, "3", 8000));
        productArrayList.add(new Product(R.drawable.desktop, "4", 5000));
        productArrayList.add(new Product(R.drawable.desktop, "5", 3000));
        productArrayList.add(new Product(R.drawable.desktop, "6", 3000));
        productArrayList.add(new Product(R.drawable.desktop, "7", 6000));

        customArrayAdapter = new CustomArrayAdapter(this, R.layout.custome_view, productArrayList);
        listView.setAdapter(customArrayAdapter);

//        arrayAdapter = new ArrayAdapter<Student>(this, android.R.layout.simple_list_item_1, studentArrayList);
//        listView.setAdapter(arrayAdapter);
    }
}