package com.ddt.myfirstapplication.SQL;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.ddt.myfirstapplication.Model.SanPham;
import com.ddt.myfirstapplication.Model.User;

import java.util.ArrayList;
import java.util.List;

public class DAOImp<T> implements DAO<T>{
    @Override
    public void Add(T entity, SQLiteDatabase sqLiteDatabase, ContentValues values, String TAG, String TABLE) {
        Log.i(TAG, "MyDatabaseHelper.add ... ");

        sqLiteDatabase.insert(TABLE, null, values);

        sqLiteDatabase.close();
    }

    @Override
    public void Delete(String id, SQLiteDatabase sqLiteDatabase, String TAG, String TABLE, String COLUMN_ID) {
        Log.i(TAG, "MyDatabaseHelper.delete ... ");

        sqLiteDatabase.delete(TABLE, COLUMN_ID + " = ?",
                new String[] { String.valueOf(id) });
        sqLiteDatabase.close();
    }

    @Override
    public int Update(SQLiteDatabase sqLiteDatabase, ContentValues values, String Id, String TAG, String TABLE, String ColumnId) {
        Log.i(TAG, "MyDatabaseHelper.update ... ");

        // updating row
        return sqLiteDatabase.update(TABLE, values, ColumnId + " = ?",
                new String[]{String.valueOf(Id)});
    }

    @Override
    public int Count(SQLiteDatabase sqLiteDatabase, String countQuery,String TAG) {
        Log.i(TAG, "MyDatabaseHelper.getCount ... " );
        Cursor cursor = sqLiteDatabase.rawQuery(countQuery, null);

        int count = cursor.getCount();

        cursor.close();

        // return count
        return count;
    }

    @Override
    public Cursor All(SQLiteDatabase sqLiteDatabase, String selectQuery, String TAG)
    {
        Log.i(TAG, "MyDatabaseHelper.getAll ... " );
//        List<T> list = new ArrayList<T>();
        Cursor cursor = sqLiteDatabase.rawQuery(selectQuery, null);
        return cursor;

//        if (cursor.moveToFirst()) {
//            do {
//                switch (type) {
//                    case "USER":
//                    {
//                        User user = new User();
//                        user.setId(cursor.getString(0));
//                        user.setEmail(cursor.getString(1));
//                        user.setPhone(cursor.getString(2));
//                        list.add(user);
//                    }
//                }
//                SanPham sanPham = new SanPham();
//                sanPham.setId(cursor.getString(0));
//                sanPham.setName(cursor.getString(1));
//                sanPham.setIsInventory(Integer.parseInt(cursor.getString(2)));
//                // Adding note to list
//                sanPhamList.add(sanPham);
//            } while (cursor.moveToNext());
//        }
    }
}
