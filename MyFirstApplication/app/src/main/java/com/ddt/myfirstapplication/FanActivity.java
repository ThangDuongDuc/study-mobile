package com.ddt.myfirstapplication;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class FanActivity extends AppCompatActivity {
    private Button btnSave;
    private TextView textViewFan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fan);

        this.btnSave = findViewById(R.id.btn_saveFan);
        this.textViewFan = findViewById(R.id.txt_valuesFan);

        this.btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences sharedPreferences = getSharedPreferences("Fan", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                String temp = textViewFan.getText().toString().trim();
                if (temp.equals("")) {
                    return;
                }
                editor.putString("values", temp);

                editor.apply();
                Toast.makeText(FanActivity.this, "saved", Toast.LENGTH_SHORT).show();
            }
        });

        this.loadStore();
    }

    private void loadStore() {
        SharedPreferences sharedPreferences = this.getSharedPreferences("Fan", Context.MODE_PRIVATE);

        if (sharedPreferences != null) {
            String values = sharedPreferences.getString("values", "");
            this.textViewFan.setText(values);
        }
        else {
            this.textViewFan.setText("default");
            Toast.makeText(this, "defautl", Toast.LENGTH_SHORT).show();
        }
    }
}