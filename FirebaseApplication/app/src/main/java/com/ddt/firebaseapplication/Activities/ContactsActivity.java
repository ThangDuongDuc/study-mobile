package com.ddt.firebaseapplication.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.ddt.firebaseapplication.Customs.ContactsAdapterCustom;
import com.ddt.firebaseapplication.Entities.Person;
import com.ddt.firebaseapplication.Firebases.ContactsFirebase;
import com.ddt.firebaseapplication.R;
import com.ddt.firebaseapplication.dtos.PersonDTO;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

public class ContactsActivity extends AppCompatActivity {
    private DatabaseReference databaseReference;
    private EditText txt_Name, txt_Phone;
    private Button btn_Add, btn_Delete;
    private ListView listView;
    private static ContactsFirebase contactsFirebase = new ContactsFirebase();
    private ArrayList<Person> personList = new ArrayList<>();
    ArrayAdapter arrayAdapter;
    ContactsAdapterCustom contactsAdapterCustom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts);

        this.binding();
        databaseReference = FirebaseDatabase.getInstance("https://mobile1604-da603-default-rtdb.asia-southeast1.firebasedatabase.app").getReference("contacts");

//        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                System.out.println("/// " + i);
//            }
//        });

        btn_Add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = txt_Name.getText().toString().trim();
                String phone = txt_Phone.getText().toString().trim();
                if (!name.equals("") && !phone.equals("")) {
                    Person person = new Person(UUID.randomUUID(), name, phone);
                    contactsFirebase.Add(databaseReference, person);
//                    databaseReference.child(person.getId().toString()).setValue(person.toString());
//                    databaseReference.child(person.getId().toString()).child("name").setValue(person.getName());
//                    databaseReference.child(person.getId().toString()).child("phone").setValue(person.getPhone());
                    Toast.makeText(ContactsActivity.this, "Successfully add contact", Toast.LENGTH_SHORT).show();
                    loading();
                }
                else {
                    Toast.makeText(ContactsActivity.this, "Field(s) empty!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btn_Delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String phone = txt_Phone.getText().toString().trim();
                if (!phone.equals("")) {
                    contactsFirebase.Delete(databaseReference, personList, phone);
                    txt_Name.setText("");
                    txt_Phone.setText("");
//                    String key = "";
//                    for (Person person:
//                            personList) {
//                        System.out.println(">>>: " + person);
//                        if (person.getPhone().equals(phone)) {
//                            System.out.println("Hi ");
//                            key = person.getId().toString();
//                            break;
//                        }
//                    }
//                    if (!key.equals("")) {
//                        databaseReference.child(key).removeValue();
//                        Toast.makeText(ContactsActivity.this, "Successfully delete contact", Toast.LENGTH_SHORT).show();
//                        loading();
//                    }
//                    else {
//                        Toast.makeText(ContactsActivity.this, "Unsuccessfully delete contact", Toast.LENGTH_SHORT).show();
//                    }
                }
                else {
                    Toast.makeText(ContactsActivity.this, "Field(s) empty!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        loading();
    }

    private void loading() {
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                personList.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Person person = Person.builder()
                                            .Id(UUID.fromString(dataSnapshot.getKey()))
                                            .name(dataSnapshot.child("name").getValue(String.class))
                                            .phone(dataSnapshot.child("phone").getValue(String.class))
                                            .build();
//                    person.setId(UUID.fromString(dataSnapshot.getKey()));
                    personList.add(person);
                }

//                arrayAdapter = new ArrayAdapter<Person>(ContactsActivity.this, android.R.layout.simple_list_item_1, personList);
//                listView.setAdapter(arrayAdapter);
                contactsAdapterCustom = new ContactsAdapterCustom(ContactsActivity.this, R.layout.activity_custom_contacts, personList);
                listView.setAdapter(contactsAdapterCustom);
                contactsAdapterCustom.notifyDataSetChanged();
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        Person person = personList.get(i);
                        txt_Name.setText(person.getName());
                        txt_Phone.setText(person.getPhone());
                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                System.out.println("The read failed: " + error.getCode());
            }
        });
    }

    private void binding(){
        this.btn_Add = findViewById(R.id.btn_Add);
        this.btn_Delete = findViewById(R.id.btn_Delete);
        this.txt_Name = findViewById(R.id.txtName);
        this.txt_Phone = findViewById(R.id.txtPhone);
        this.listView = findViewById(R.id.listView);
    }
}