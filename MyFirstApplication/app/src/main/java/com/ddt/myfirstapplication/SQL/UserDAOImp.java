package com.ddt.myfirstapplication.SQL;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.Nullable;
import android.util.Log;

import com.ddt.myfirstapplication.Model.SanPham;
import com.ddt.myfirstapplication.Model.User;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class UserDAOImp {
    private static final String TABLE_USER = "User";

    private static final String COLUMN_PERSON_ID ="Id";
    private static final String COLUMN_PERSON_EMAIL ="Email";
    private static final String COLUMN_PERSON_PHONE = "Phone";
    private static final String COLUMN_CUSTOMER_NAME = "Name";
    private static final String COLUMN_CUSTOMER_ADDRESS = "Address";

    private static DAOImp<User> userDAO = new DAOImp<User>();

    public void Delete(SQLiteDatabase sqLiteDatabase, String TAG, String Id) {
        userDAO.Delete(Id, sqLiteDatabase, TAG, TABLE_USER, COLUMN_PERSON_ID);
    }

    public int Update(User entity, SQLiteDatabase sqLiteDatabase, String TAG) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_PERSON_ID, entity.getId());
        values.put(COLUMN_PERSON_EMAIL, entity.getEmail());
        values.put(COLUMN_PERSON_PHONE, entity.getPhone());

        return userDAO.Update(sqLiteDatabase, values, entity.getId().toString(), TAG, TABLE_USER, COLUMN_PERSON_ID);
    }

    public int Count(SQLiteDatabase sqLiteDatabase, String TAG) {
        String countQuery = "SELECT * FROM " + TABLE_USER;
        return userDAO.Count(sqLiteDatabase, countQuery, TAG);
    }

    public void Add(User entity, SQLiteDatabase sqLiteDatabase, String TAG) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_PERSON_ID, entity.getId());
        values.put(COLUMN_PERSON_EMAIL, entity.getEmail());
        values.put(COLUMN_PERSON_PHONE, entity.getPhone());

        userDAO.Add(entity, sqLiteDatabase, values , TAG, TABLE_USER);
    }

    public List<User> getAll(SQLiteDatabase sqLiteDatabase, String TAG) {
        List<User> list = new ArrayList<User>();
        // Select All Query
        String selectQuery = "SELECT * FROM " + TABLE_USER;
        Cursor cursor = userDAO.All(sqLiteDatabase, selectQuery, TAG);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                User user = new User();
                user.setId(cursor.getString(0));
                user.setEmail(cursor.getString(1));
                user.setPhone(cursor.getString(2));

                list.add(user);
            } while (cursor.moveToNext());
        }

        // return note list
        return list;
    }
}
