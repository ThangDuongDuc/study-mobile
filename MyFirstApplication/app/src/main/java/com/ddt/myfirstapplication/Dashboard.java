package com.ddt.myfirstapplication;

import android.content.Intent;
import android.net.Uri;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class Dashboard extends AppCompatActivity {
    TextView text;
    EditText cty;
    Button btn_cty;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        text  = findViewById(R.id.textView);
        cty = findViewById(R.id.cty);
        btn_cty = findViewById(R.id.btn_cty);
        Intent intent = getIntent();
        text.setText(intent.getStringExtra("id"));
        cty.setText(intent.getStringExtra("cty"));

        btn_cty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(Intent.ACTION_VIEW);
                intent1.setData(Uri.parse(cty.getText().toString()));
                startActivity(intent1);
            }
        });
    }
}