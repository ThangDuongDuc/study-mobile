package com.ddt.firebaseapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.os.Bundle;
import android.view.View;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {
    private DatabaseReference databaseReference;
    private static final int MY_PERMISSIONS_REQUEST_CALL = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void CallActivity(View view) {
        ActivityCompat.requestPermissions(this,
                new String[] {Manifest.permission.CALL_PHONE},
                MY_PERMISSIONS_REQUEST_CALL);
    }
}