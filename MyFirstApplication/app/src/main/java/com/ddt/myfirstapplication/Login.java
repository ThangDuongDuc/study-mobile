package com.ddt.myfirstapplication;

import android.content.Intent;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.ddt.myfirstapplication.Model.Student;

public class Login extends AppCompatActivity {
    EditText txt_Username;
    EditText txt_Password;
    Button btn_OK;
    Button btn_CANCEL;
    Button btn_FORGOTPWD;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        this.getViews();
        btn_OK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getApplicationContext(), Dashboard.class);
                Student a = new Student("1", "https://petservicebe.herokuapp.com/swagger-ui/index.html#/", 9.9);
                intent.putExtra("id", a.id);
                intent.putExtra("cty", a.name);
                startActivity(intent);
            }
        });
    }

    private void getViews() {
        txt_Username = findViewById(R.id.txt_Username);
        txt_Password = findViewById(R.id.txt_Password);
        btn_OK = findViewById(R.id.btn_OK);
        btn_CANCEL = findViewById(R.id.btn_CANCEL);
        btn_FORGOTPWD = findViewById(R.id.btn_FORGOTPWD);
    }

    public void onclickOK(View view) {
        if (this.txt_Username.getText().toString().trim().equals("") || this.txt_Password.getText().toString().trim().equals("")) {
            Toast.makeText(this, "Empty Fields", Toast.LENGTH_SHORT).show();
        }
    }

    public void onclickCLEAR(View view) {
        this.txt_Username.setText("");
        this.txt_Password.setText("");
    }

    public void onclickFORGOTPWD(View view) {
        if (this.txt_Username.getText().toString().trim().equals("")) {
            Toast.makeText(this, "Empty Fields", Toast.LENGTH_SHORT).show();
        }
    }
}