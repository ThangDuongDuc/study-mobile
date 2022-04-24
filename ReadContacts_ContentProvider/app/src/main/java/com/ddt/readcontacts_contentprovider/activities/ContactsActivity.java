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
//                String phone = txtPhone.getText().toString().trim();
                if (name.equals("") ) {
                    Toast.makeText(ContactsActivity.this, "Field(s) empty!", Toast.LENGTH_SHORT).show();
                }
                else {
                    if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.M
                            &&  checkSelfPermission(Manifest.permission.WRITE_CONTACTS)!= PackageManager.PERMISSION_GRANTED) {
                        requestPermissions(new String[]{Manifest.permission.WRITE_CONTACTS}, 1);
                    }
                    deleteContact(name);
                    Toast.makeText(ContactsActivity.this, "Delete success", Toast.LENGTH_SHORT).show();
                    showAllContacts();
                    txt_Name.setText("");
                    txtPhone.setText("");
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

    private void deleteContact(String name)
    {
        // First select raw contact id by given name and family name.
        long rawContactId = getRawContactIdByName(name);

        ContentResolver contentResolver = getContentResolver();

        //******************************* delete data table related data ****************************************
        // Data table content process uri.
        Uri dataContentUri = ContactsContract.Data.CONTENT_URI;

        // Create data table where clause.
        StringBuffer dataWhereClauseBuf = new StringBuffer();
        dataWhereClauseBuf.append(ContactsContract.Data.RAW_CONTACT_ID);
        dataWhereClauseBuf.append(" = ");
        dataWhereClauseBuf.append(rawContactId);

        // Delete all this contact related data in data table.
        contentResolver.delete(dataContentUri, dataWhereClauseBuf.toString(), null);


        //******************************** delete raw_contacts table related data ***************************************
        // raw_contacts table content process uri.
        Uri rawContactUri = ContactsContract.RawContacts.CONTENT_URI;

        // Create raw_contacts table where clause.
        StringBuffer rawContactWhereClause = new StringBuffer();
        rawContactWhereClause.append(ContactsContract.RawContacts._ID);
        rawContactWhereClause.append(" = ");
        rawContactWhereClause.append(rawContactId);

        // Delete raw_contacts table related data.
        contentResolver.delete(rawContactUri, rawContactWhereClause.toString(), null);

        //******************************** delete contacts table related data ***************************************
        // contacts table content process uri.
        Uri contactUri = ContactsContract.Contacts.CONTENT_URI;

        // Create contacts table where clause.
        StringBuffer contactWhereClause = new StringBuffer();
        contactWhereClause.append(ContactsContract.Contacts._ID);
        contactWhereClause.append(" = ");
        contactWhereClause.append(rawContactId);

        // Delete raw_contacts table related data.
        contentResolver.delete(contactUri, contactWhereClause.toString(), null);

    }

    @SuppressLint("Range")
    private long getRawContactIdByName(String name)
    {
        ContentResolver contentResolver = getContentResolver();

        // Query raw_contacts table by display name field ( given_name family_name ) to get raw contact id.

        // Create query column array.
        String queryColumnArr[] = {ContactsContract.RawContacts._ID};

        // Create where condition clause.
        String displayName = name;
        String whereClause = ContactsContract.RawContacts.DISPLAY_NAME_PRIMARY + " = '" + displayName + "'";

        // Query raw contact id through RawContacts uri.
        Uri rawContactUri = ContactsContract.RawContacts.CONTENT_URI;

        // Return the query cursor.
        Cursor cursor = contentResolver.query(rawContactUri, queryColumnArr, whereClause, null, null);

        long rawContactId = -1;

        if(cursor!=null)
        {
            // Get contact count that has same display name, generally it should be one.
            int queryResultCount = cursor.getCount();
            // This check is used to avoid cursor index out of bounds exception. android.database.CursorIndexOutOfBoundsException
            if(queryResultCount > 0)
            {
                // Move to the first row in the result cursor.
                cursor.moveToFirst();
                // Get raw_contact_id.
                rawContactId = cursor.getLong(cursor.getColumnIndex(ContactsContract.RawContacts._ID));
            }
        }
        return rawContactId;
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