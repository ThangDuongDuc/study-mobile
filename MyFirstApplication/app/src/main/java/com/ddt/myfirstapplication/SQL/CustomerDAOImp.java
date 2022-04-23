package com.ddt.myfirstapplication.SQL;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.ddt.myfirstapplication.Model.Customer;
import com.ddt.myfirstapplication.Model.User;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class CustomerDAOImp {
    private static final String TABLE_USER = "Customer";

    private static final String COLUMN_PERSON_ID ="Id";
    private static final String COLUMN_PERSON_EMAIL ="Email";
    private static final String COLUMN_PERSON_PHONE = "Phone";
    private static final String COLUMN_CUSTOMER_NAME = "Name";
    private static final String COLUMN_CUSTOMER_ADDRESS = "Address";

    private static DAOImp<Customer> customerDAO = new DAOImp<Customer>();

    public List<Customer> getAll (SQLiteDatabase sqLiteDatabase, String TAG) {
        List<Customer> list = new ArrayList<Customer>();
        // Select All Query
        String selectQuery = "SELECT * FROM " + TABLE_USER;
        Cursor cursor = customerDAO.All(sqLiteDatabase, selectQuery, TAG);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Customer customer = new Customer();
                customer.setId(cursor.getString(0));
                customer.setEmail(cursor.getString(1));
                customer.setPhone(cursor.getString(2));
                customer.setName(cursor.getString(3));
                customer.setAddress(cursor.getString(4));

                list.add(customer);
            } while (cursor.moveToNext());
        }

        // return note list
        return list;
    }

    public int Count(SQLiteDatabase sqLiteDatabase, String TAG) {
        String countQuery = "SELECT * FROM " + TABLE_USER;
        return customerDAO.Count(sqLiteDatabase, countQuery, TAG);
    }

    public void Delete(SQLiteDatabase sqLiteDatabase, String TAG, String Id) {
        customerDAO.Delete(Id, sqLiteDatabase, TAG, TABLE_USER, COLUMN_PERSON_ID);
    }

    public int Update(Customer entity, SQLiteDatabase sqLiteDatabase, String TAG) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_PERSON_ID, entity.getId());
        values.put(COLUMN_PERSON_EMAIL, entity.getEmail());
        values.put(COLUMN_PERSON_PHONE, entity.getPhone());
        values.put(COLUMN_CUSTOMER_NAME, entity.getName());
        values.put(COLUMN_CUSTOMER_ADDRESS, entity.getAddress());

        return customerDAO.Update(sqLiteDatabase, values, entity.getId().toString(), TAG, TABLE_USER, COLUMN_PERSON_ID);
    }

    public void Add(Customer entity, SQLiteDatabase sqLiteDatabase, String TAG) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_PERSON_ID, entity.getId());
        values.put(COLUMN_PERSON_EMAIL, entity.getEmail());
        values.put(COLUMN_PERSON_PHONE, entity.getPhone());
        values.put(COLUMN_CUSTOMER_NAME, entity.getName());
        values.put(COLUMN_CUSTOMER_ADDRESS, entity.getAddress());

        customerDAO.Add(entity, sqLiteDatabase, values , TAG, TABLE_USER);
    }
}
