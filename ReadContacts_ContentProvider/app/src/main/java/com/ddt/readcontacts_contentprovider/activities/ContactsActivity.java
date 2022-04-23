package com.ddt.readcontacts_contentprovider.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.ContentProviderOperation;
import android.content.ContentResolver;
import android.content.OperationApplicationException;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.RemoteException;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.ddt.readcontacts_contentprovider.R;
import com.ddt.readcontacts_contentprovider.adapters.ContactsAdapterCustom;
import com.ddt.readcontacts_contentprovider.models.Contact;

import java.util.ArrayList;

public class ContactsActivity extends AppCompatActivity {
    private static final int REQUEST_CONTACTS_ASK_PERMISSIONS = 1001;
    private EditText txt_Name, txtPhone;
    private Button btn_Add, btn_Delete;
    private ListView listViewContacts;
    ArrayList<Contact> contactArrayList= new ArrayList<>();;
    ArrayAdapter<Contact> contactArrayAdapter;
    ContactsAdapterCustom contactsAdapterCustom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts);

        this.binding();
        if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.M
                &&  checkSelfPermission(Manifest.permission.READ_CONTACTS)!= PackageManager.PERMISSION_GRANTED)
        {
            requestPermissions(new String[]{Manifest.permission.READ_CONTACTS},1);
        }
        else
        {
//            addControls();
            showAllContacts();
        }

        btn_Add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = txt_Name.getText().toString().trim();
                String phone = txtPhone.getText().toString().trim();
                if (name.equals("") || phone.equals("")) {
                    Toast.makeText(ContactsActivity.this, "Field(s) empty!", Toast.LENGTH_SHORT).show();
                }
                else {
                    if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.M
                            &&  checkSelfPermission(Manifest.permission.WRITE_CONTACTS)!= PackageManager.PERMISSION_GRANTED) {
                        requestPermissions(new String[]{Manifest.permission.WRITE_CONTACTS}, 1);
                    }
                    writeContact(name, phone);
                    Toast.makeText(ContactsActivity.this, "Add success", Toast.LENGTH_SHORT).show();
//                    addControls();
                    showAllContacts();
                }
            }
        });

        btn_Delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = txt_Name.getText().toString().trim();
                String phone = txtPhone.getText().toString().trim();
                if (name.equals("") || phone.equals("")) {
                    Toast.makeText(ContactsActivity.this, "Field(s) empty!", Toast.LENGTH_SHORT).show();
                }
                else {
                    if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.M
                            &&  checkSelfPermission(Manifest.permission.WRITE_CONTACTS)!= PackageManager.PERMISSION_GRANTED) {
                        requestPermissions(new String[]{Manifest.permission.WRITE_CONTACTS}, 1);
                    }
                    deleteContact(name, phone);
                    Toast.makeText(ContactsActivity.this, "Delete success", Toast.LENGTH_SHORT).show();
//                    addControls();
                    showAllContacts();
//                    if (result) {
//
//                    }
//                    else {
//                        Toast.makeText(ContactsActivity.this, "Delete unsuccessfully", Toast.LENGTH_SHORT).show();
//                    }
                }
            }
        });
    }

    public void deleteContact(String phone, String name) {
        ArrayList<ContentProviderOperation> ops = new ArrayList<ContentProviderOperation>();
        String[] args = new String[] { String.valueOf(getContactID(phone)) };
        ops.add(ContentProviderOperation.newDelete(ContactsContract.RawContacts.CONTENT_URI).withSelection(ContactsContract.RawContacts.CONTACT_ID + "=?", args).build());
        try {
            getApplicationContext().getContentResolver().applyBatch(ContactsContract.AUTHORITY, ops);
        } catch (RemoteException e) {
            e.printStackTrace();
        } catch (OperationApplicationException e) {
            e.printStackTrace();
        }
    }

    private long getContactID(String number) {
        Uri contactUri = Uri.withAppendedPath(ContactsContract.PhoneLookup.CONTENT_FILTER_URI, Uri.encode(number));
        String[] projection = { ContactsContract.PhoneLookup._ID };
        Cursor cursor = null;

        try {
            cursor = getApplicationContext().getContentResolver().query(contactUri, projection, null, null,null);
            if (cursor.moveToFirst()) {
                int personID = cursor.getColumnIndex(ContactsContract.PhoneLookup._ID);
                return cursor.getLong(personID);
            }

//            int personID = cursor.getColumnIndex(ContactsContract.PhoneLookup._ID);
            return -1;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
                cursor = null;
            }
        }
        return -1;
    }

    private void writeContact(String displayName, String number) {
        ArrayList contentProviderOperations = new ArrayList();
        //insert raw contact using RawContacts.CONTENT_URI
        contentProviderOperations.add(ContentProviderOperation.newInsert(ContactsContract.RawContacts.CONTENT_URI)
                .withValue(ContactsContract.RawContacts.ACCOUNT_TYPE, null).withValue(ContactsContract.RawContacts.ACCOUNT_NAME, null).build());
        //insert contact display name using Data.CONTENT_URI
        contentProviderOperations.add(ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI)
                .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, 0).withValue(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.StructuredName.CONTENT_ITEM_TYPE)
                .withValue(ContactsContract.CommonDataKinds.StructuredName.DISPLAY_NAME, displayName).build());
        //insert mobile number using Data.CONTENT_URI
        contentProviderOperations.add(ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI)
                .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, 0).withValue(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE)
                .withValue(ContactsContract.CommonDataKinds.Phone.NUMBER, number).withValue(ContactsContract.CommonDataKinds.Phone.TYPE, ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE).build());
        try {
            getApplicationContext().getContentResolver().
                    applyBatch(ContactsContract.AUTHORITY, contentProviderOperations);
        } catch (RemoteException| OperationApplicationException e) {
            e.printStackTrace();
        }
    }

    private void showAllContacts() {
        Uri uri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;
        Cursor cursor = getContentResolver().query(uri, null, null, null, null);
        contactArrayList.clear();
        while (cursor.moveToNext()) {
            String NameCol = ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME;
            String PhoneCol = ContactsContract.CommonDataKinds.Phone.NUMBER;
            int position_Name = cursor.getColumnIndex(NameCol);
            int position_Phone = cursor.getColumnIndex(PhoneCol);
            @SuppressLint("Range") String name = cursor.getString(position_Name);
            @SuppressLint("Range") String phone = cursor.getString(position_Phone);
            Contact contact = Contact.builder()
                                .name(name)
                                .phone(phone)
                                .build();
            contactArrayList.add(contact);
        }
        contactsAdapterCustom = new ContactsAdapterCustom(ContactsActivity.this, R.layout.custom_contacts, contactArrayList);
        listViewContacts.setAdapter(contactsAdapterCustom);
        contactsAdapterCustom.notifyDataSetChanged();
        listViewContacts.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Contact contact = contactArrayList.get(i);
                txt_Name.setText(contact.getName());
                txtPhone.setText(contact.getPhone());
            }
        });
    }

    private void addControls() {
//        contactArrayList = new ArrayList<>();
        contactArrayAdapter = new ArrayAdapter<>(ContactsActivity.this, android.R.layout.simple_list_item_1, contactArrayList);
        contactsAdapterCustom = new ContactsAdapterCustom(ContactsActivity.this, R.layout.custom_contacts, contactArrayList);
        listViewContacts.setAdapter(contactArrayAdapter);
        contactArrayAdapter.notifyDataSetChanged();
    }

    private void binding() {
        this.listViewContacts = findViewById(R.id.listViewContacts);
        this.txt_Name = findViewById(R.id.txtName);
        this.txtPhone = findViewById(R.id.txtPhone);
        this.btn_Add = findViewById(R.id.btn_Add);
        this.btn_Delete = findViewById(R.id.btn_Delete);
    }
}