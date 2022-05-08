package com.ddt.lab_contentprovider.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.ddt.lab_contentprovider.MainActivity;
import com.ddt.lab_contentprovider.Model.Task;
import com.ddt.lab_contentprovider.R;
import com.ddt.lab_contentprovider.SQL.MyDatabaseHelper;
import com.google.gson.Gson;

import java.util.Locale;
import java.util.UUID;

public class AddOrDeleteActivity extends AppCompatActivity {
    EditText txt_nameTask;
    EditText txt_descriptionTask;
    Button btn_Action;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_or_delete);
        MyDatabaseHelper myDatabaseHelper = new MyDatabaseHelper(this);

        this.binding();

        this.setTextButton();

        this.actionClickButton(myDatabaseHelper);
    }

    private void actionClickButton(MyDatabaseHelper myDatabaseHelper){
        Intent intent = getIntent();
        String action = intent.getStringExtra("ACTION");
        Intent intent1 = new Intent(AddOrDeleteActivity.this, MainActivity.class);
        if (action.toLowerCase(Locale.ROOT).equals("add")) {
            this.btn_Action.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (txt_nameTask.getText().equals("") || txt_descriptionTask.getText().equals("")){
                        Toast.makeText(AddOrDeleteActivity.this, "Fields Empty", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        Task task = new Task().builder()
                                .Id(UUID.randomUUID())
                                .Name(txt_nameTask.getText().toString().trim())
                                .Description(txt_descriptionTask.getText().toString().trim())
                                .build();
                        myDatabaseHelper.save(task);
                        startActivity(intent1);
                    }
                }
            });
        }
        else {
            this.btn_Action.setText("UPDATE");
            String TaskJson = intent.getStringExtra("TaskJson");
            Gson gson = new Gson();
            Task task = gson.fromJson(TaskJson, Task.class);
            txt_nameTask.setText(task.getName());
            txt_descriptionTask.setText(task.getDescription());
            btn_Action.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (txt_nameTask.getText().equals("") || txt_descriptionTask.getText().equals("")){
                    Toast.makeText(AddOrDeleteActivity.this, "Fields Empty", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        Task task1 = new Task().builder()
                            .Id(task.getId())
                            .Name(txt_nameTask.getText().toString().trim())
                            .Description(txt_descriptionTask.getText().toString().trim())
                            .build();
                        myDatabaseHelper.update(task1);
                        startActivity(intent1);
                    }
                }
            });
        }
    }

    private void setTextButton() {
        Intent intent = getIntent();
        String action = intent.getStringExtra("ACTION");
        if (action.toLowerCase(Locale.ROOT).equals("add")) {
            this.btn_Action.setText("ADD");
        }
        else {
            this.btn_Action.setText("UPDATE");
        }
    }

    private void binding() {
        this.txt_nameTask = findViewById(R.id.txt_nameTask);
        this.txt_descriptionTask = findViewById(R.id.txt_descriptionTask);
        this.btn_Action = findViewById(R.id.btn_Action);
    }
}