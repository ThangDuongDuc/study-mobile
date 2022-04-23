package com.ddt.myfirstapplication.SQL;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;
import android.util.Log;

import com.ddt.myfirstapplication.Model.SanPham;

import java.util.ArrayList;
import java.util.List;

public class ProductOpenHelper extends SQLiteOpenHelper {
    private static final String TAG = "SQLite";
    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "ProductDatabase";

    // Table name: Product.
    private static final String PRODUCT = "Product";

    private static final String COLUMN_PRODUCT_ID ="Id";
    private static final String COLUMN_PRODUCT_NAME ="Name";
    private static final String COLUMN_PRODUCT_ISINVENTORY = "IsInventory";
    public ProductOpenHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        Log.i(TAG, "MyDatabaseHelper.onCreate ... ");
        // Script to create table.
        String script = "CREATE TABLE IF NOT EXISTS " + PRODUCT + "("
                + COLUMN_PRODUCT_ID + " TEXT PRIMARY KEY," + COLUMN_PRODUCT_NAME + " TEXT,"
                + COLUMN_PRODUCT_ISINVENTORY + " INTEGER" + ")";
        // Execute script.
        sqLiteDatabase.execSQL(script);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        Log.i(TAG, "MyDatabaseHelper.onUpgrade ... ");
        // Drop older table if existed
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + PRODUCT);

        // Create tables again
        onCreate(sqLiteDatabase);
    }

    public void createDefaultSanPhamsIfNeed()  {
        int count = this.getSanPhamsCount();
        if(count == 0 ) {
            SanPham sanPham1 = new SanPham("thang01", "thangCute01", 1);
            SanPham sanPham2 = new SanPham("thang02", "thangCute02", 12);

            this.addSanPham(sanPham1);
            this.addSanPham(sanPham2);
        }
    }

    public int getSanPhamsCount() {
        Log.i(TAG, "MyDatabaseHelper.getSanPhamsCount ... " );

        String countQuery = "SELECT * FROM " + PRODUCT;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);

        int count = cursor.getCount();

        cursor.close();

        // return count
        return count;
    }

    public void addSanPham(SanPham sanPham) {
        Log.i(TAG, "MyDatabaseHelper.addSanPham ... " + sanPham.getName());
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_PRODUCT_ID, sanPham.getId());
        values.put(COLUMN_PRODUCT_NAME, sanPham.getName());
        values.put(COLUMN_PRODUCT_ISINVENTORY, sanPham.getIsInventory());

        sqLiteDatabase.insert(PRODUCT, null, values);

        sqLiteDatabase.close();
    }

    public int updateSanPham(SanPham sanPham) {
        Log.i(TAG, "MyDatabaseHelper.updateSanPham ... "  + sanPham.getName());

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_PRODUCT_ID, sanPham.getId());
        values.put(COLUMN_PRODUCT_NAME, sanPham.getName());
        values.put(COLUMN_PRODUCT_ISINVENTORY, sanPham.getIsInventory());

        // updating row
        return db.update(PRODUCT, values, COLUMN_PRODUCT_ID + " = ?",
                new String[]{String.valueOf(sanPham.getId())});
    }

    public void deleteSanPham(String Id) {
        Log.i(TAG, "MyDatabaseHelper.deleteSanPham ... " + Id);

        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(PRODUCT, COLUMN_PRODUCT_ID + " = ?",
                new String[] { String.valueOf(Id) });
        db.close();
    }

    public List<SanPham> getAllSanPham() {
        Log.i(TAG, "MyDatabaseHelper.getAllSanPham ... " );

        List<SanPham> sanPhamList = new ArrayList<SanPham>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + PRODUCT;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                SanPham sanPham = new SanPham();
                sanPham.setId(cursor.getString(0));
                sanPham.setName(cursor.getString(1));
                sanPham.setIsInventory(Integer.parseInt(cursor.getString(2)));
                // Adding note to list
                sanPhamList.add(sanPham);
            } while (cursor.moveToNext());
        }

        // return note list
        return sanPhamList;
    }
}
