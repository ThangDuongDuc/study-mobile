package com.ddt.myfirstapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.ddt.myfirstapplication.Custom.CustomArrayAdapter;
import com.ddt.myfirstapplication.Model.Product;

import java.util.ArrayList;

public class GridViewActivity extends AppCompatActivity {
    GridView gridView;
    CustomArrayAdapter customArrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grid_view);

        gridView = findViewById(R.id.gridView);

        ArrayList<Product> productArrayList = new ArrayList<Product>();
        productArrayList.add(new Product(R.drawable.desktop, "2", 1000));
        productArrayList.add(new Product(R.drawable.desktop, "3", 8000));
        productArrayList.add(new Product(R.drawable.desktop, "4", 5000));
        productArrayList.add(new Product(R.drawable.desktop, "5", 3000));
        productArrayList.add(new Product(R.drawable.desktop, "6", 3000));
        productArrayList.add(new Product(R.drawable.desktop, "7", 6000));

        customArrayAdapter = new CustomArrayAdapter(this, R.layout.custome_view, productArrayList);
        gridView.setAdapter(customArrayAdapter);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Product product = (Product) gridView.getItemAtPosition(i);
                Toast.makeText(getApplicationContext(), product.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}