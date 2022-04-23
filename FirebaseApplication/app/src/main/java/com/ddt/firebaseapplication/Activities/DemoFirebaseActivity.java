package com.ddt.firebaseapplication.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.ddt.firebaseapplication.R;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class DemoFirebaseActivity extends AppCompatActivity {
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo_firebase);

        databaseReference = FirebaseDatabase.getInstance("https://mobile1604-da603-default-rtdb.asia-southeast1.firebasedatabase.app").getReference("accounts");

        databaseReference.child("username").setValue("ThangDuong");
        databaseReference.child("fullname").child("firstname").setValue("Thang");
        databaseReference.child("fullname").child("lastname").setValue("Duong");
    }
}