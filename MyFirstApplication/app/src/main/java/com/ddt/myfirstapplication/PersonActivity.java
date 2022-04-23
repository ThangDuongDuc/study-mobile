package com.ddt.myfirstapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;

import com.ddt.myfirstapplication.Model.Customer;
import com.ddt.myfirstapplication.Model.SanPham;
import com.ddt.myfirstapplication.Model.User;
import com.ddt.myfirstapplication.SQL.MyDatabaseHelper;
import com.ddt.myfirstapplication.SQL.ProductOpenHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class PersonActivity extends AppCompatActivity {
    private ListView listView;
    private Button btn_Add, btn_Update, btn_Delete, btn_All;
    private EditText txt_id, txt_name, txt_email, txt_phone, txt_address, txt_type;
    private Spinner dropdown;

    private final List<User> userList = new ArrayList<User>();
    private ArrayAdapter<User> user_listViewAdapter;
    private ArrayAdapter<Customer> customer_listViewAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person);

        this.listView = findViewById(R.id.listperson);
        this.btn_Add = findViewById(R.id.addperson);
        this.btn_All = findViewById(R.id.allperson);
        this.btn_Delete = findViewById(R.id.deleteperson);
        this.btn_Update = findViewById(R.id.updateperson);
        this.txt_id = findViewById(R.id.idperson);
        this.txt_name = findViewById(R.id.nameperson);
        this.txt_email = findViewById(R.id.emailperson);
        this.txt_phone = findViewById(R.id.phoneperson);
        this.txt_address = findViewById(R.id.addressperson);
//        this.txt_type = findViewById(R.id.typeperson);
        this.dropdown = findViewById(R.id.spinner1);

        String[] items = new String[]{"USER", "CUSTOMER"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items);
        dropdown.setAdapter(adapter);

        MyDatabaseHelper myDatabaseHelper = new MyDatabaseHelper(this);
        myDatabaseHelper.createDefault();
        List<User> list =  myDatabaseHelper.getAllUser();

        this.user_listViewAdapter = new ArrayAdapter<User>(this,
                android.R.layout.simple_list_item_1, list);

        // Assign adapter to ListView
        this.listView.setAdapter(this.user_listViewAdapter);

        registerForContextMenu(this.listView);

        btn_Add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                String a = dropdown.getSelectedItem().toString();
//                txt_email.setText(a);
                if (dropdown.getSelectedItem().toString().trim().toLowerCase(Locale.ROOT).equals("user")) {
                    String Id = txt_id.getText().toString().trim();
                    String email = txt_email.getText().toString().trim();
                    String phone = txt_phone.getText().toString().trim();
                    User user = new User(Id, email, phone);
                    myDatabaseHelper.addUser(user);
                }
                else {
                    String Id = txt_id.getText().toString().trim();
                    String email = txt_email.getText().toString().trim();
                    String phone = txt_phone.getText().toString().trim();
                    String address = txt_address.getText().toString().trim();
                    String name = txt_name.getText().toString().trim();
//                    User user = new User(Id, email, phone);
                    Customer customer = new Customer(Id, email, phone, name, address);
                    myDatabaseHelper.updateCustomer(customer);
                }
                btn_All.callOnClick();
            }
        });

        btn_Update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (dropdown.getSelectedItem().toString().trim().toLowerCase(Locale.ROOT).equals("user")) {
                    String Id = txt_id.getText().toString().trim();
                    String email = txt_email.getText().toString().trim();
                    String phone = txt_phone.getText().toString().trim();
                    User user = new User(Id, email, phone);
                    myDatabaseHelper.updateUser(user);
                }
                else {
                    String Id = txt_id.getText().toString().trim();
                    String email = txt_email.getText().toString().trim();
                    String phone = txt_phone.getText().toString().trim();
                    String address = txt_address.getText().toString().trim();
                    String name = txt_name.getText().toString().trim();
//                    User user = new User(Id, email, phone);
                    Customer customer = new Customer(Id, email, phone, name, address);
                    myDatabaseHelper.updateCustomer(customer);

                }
                btn_All.callOnClick();
            }
        });

        btn_All.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (dropdown.getSelectedItem().toString().trim().toLowerCase(Locale.ROOT).equals("user")) {
                    List<User> list =  myDatabaseHelper.getAllUser();

                    user_listViewAdapter = new ArrayAdapter<User>(PersonActivity.this,
                            android.R.layout.simple_list_item_1, list);

                    // Assign adapter to ListView
                    listView.setAdapter(user_listViewAdapter);
                }
                else {
                    List<Customer> customers = myDatabaseHelper.getAllCustomer();

                    customer_listViewAdapter = new ArrayAdapter<Customer>(PersonActivity.this,
                            android.R.layout.simple_list_item_1, customers);

                    listView.setAdapter(customer_listViewAdapter);
                }
            }
        });

        btn_Delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (dropdown.getSelectedItem().toString().trim().toLowerCase(Locale.ROOT).equals("user")) {
                    if (!txt_id.getText().toString().trim().equals("")) {
                        myDatabaseHelper.deleteUser(txt_id.getText().toString().trim());
                    }
                }
                else {
                    if (!txt_id.getText().toString().trim().equals("")) {
                        myDatabaseHelper.deleteCustomer(txt_id.getText().toString().trim());
                    }
                }
                btn_All.callOnClick();
            }
        });
    }
}