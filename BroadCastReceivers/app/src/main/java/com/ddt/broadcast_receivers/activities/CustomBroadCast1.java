package com.ddt.broadcast_receivers.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.ddt.broadcast_receivers.R;

public class CustomBroadCast1 extends AppCompatActivity {
    private final String CUSTOM_ACTION = "ddt.ACTION";
    private final String CUSTOM_KEY = "ddt.KEY";
    Button btnSend;
    EditText editContent;
    TextView txtContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_board_cast1);

        bind();
        handleEvent();
    }

    private void bind(){
        btnSend = findViewById(R.id.btnSend);
        editContent = findViewById(R.id.editTextContent);
    }

    private void handleEvent(){
        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CUSTOM_ACTION);
                intent.putExtra(CUSTOM_KEY, editContent.getText().toString());
                sendBroadcast(intent);
            }
        });
    }
}