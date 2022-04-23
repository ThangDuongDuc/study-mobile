package com.ddt.myfirstapplication.SQL;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.List;

public interface DAO<T> {
    public void Add(T entity, SQLiteDatabase sqLiteDatabase, ContentValues values, String TAG, String TABLE);
    public void Delete(String id, SQLiteDatabase sqLiteDatabase, String TAG, String TABLE, String COLUMN_ID);
    public int Update(SQLiteDatabase sqLiteDatabase, ContentValues values, String Id, String TAG, String TABLE, String ColumnId);
    public int Count(SQLiteDatabase sqLiteDatabase, String countQuery,String TAG);
    public Cursor All(SQLiteDatabase sqLiteDatabase, String selectQuery, String TAG);
}
