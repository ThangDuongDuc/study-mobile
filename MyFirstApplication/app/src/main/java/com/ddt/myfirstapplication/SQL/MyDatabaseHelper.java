package com.ddt.myfirstapplication.SQL;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;
import android.util.Log;

import com.ddt.myfirstapplication.Model.Customer;
import com.ddt.myfirstapplication.Model.Person;
import com.ddt.myfirstapplication.Model.SanPham;
import com.ddt.myfirstapplication.Model.User;

import java.util.List;

public class MyDatabaseHelper extends SQLiteOpenHelper{
    private static final String TAG = "SQLite";
    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "DB_Person";

    private static final String TABLE_USER = "User";
    private static final String TABLE_PERSON = "Customer";

    private static final String COLUMN_PERSON_ID ="Id";
    private static final String COLUMN_PERSON_EMAIL ="Email";
    private static final String COLUMN_PERSON_PHONE = "Phone";
    private static final String COLUMN_CUSTOMER_NAME = "Phone";
    private static final String COLUMN_CUSTOMER_ADDRESS = "Phone";
    private static UserDAOImp userDAOImp = new UserDAOImp();
    private static CustomerDAOImp customerDAOImp = new CustomerDAOImp();

    public MyDatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        Log.i(TAG, "MyDatabaseHelper.onCreate ... ");
        // Script to create table.
        String script1 = "CREATE TABLE IF NOT EXISTS " + TABLE_USER + "("
                + COLUMN_PERSON_ID + " TEXT PRIMARY KEY," + COLUMN_PERSON_EMAIL + " TEXT,"
                + COLUMN_PERSON_PHONE + " TEXT" + ")";

        String script2 = "CREATE TABLE IF NOT EXISTS " + TABLE_PERSON + "("
                + COLUMN_PERSON_ID + " TEXT PRIMARY KEY," + COLUMN_PERSON_EMAIL + " TEXT,"
                + COLUMN_PERSON_PHONE + " TEXT," + COLUMN_CUSTOMER_NAME + " TEXT," + COLUMN_CUSTOMER_ADDRESS + " TEXT" + ")";

//        String totalScript = script1 + " " + script2;
        sqLiteDatabase.execSQL(script1);
//        sqLiteDatabase.execSQL(totalScript);
        sqLiteDatabase.execSQL(script2);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        Log.i(TAG, "MyDatabaseHelper.onUpgrade ... ");
        // Drop older table if existed
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_PERSON);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_USER);

        // Create tables again
        onCreate(sqLiteDatabase);
    }

    public void createDefault()  {
        int countUser = this.countUser();
        if (countUser == 0) {
            User user1 = new User("1", "a", "a");
            User user2 = new User("2", "b", "b");
            this.addUser(user1);
            this.addUser(user2);
        }
//        int countCustomer = this.countCustomer();
//        if (countCustomer == 0) {
            Customer customer1 = new Customer("3", "3", "3", "3", "3");
            Customer customer2 = new Customer("4", "4", "4", "4", "4");
            this.addCustomer(customer1);
            this.addCustomer(customer2);

//        }
    }

    public void deleteUser(String id) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        userDAOImp.Delete(sqLiteDatabase, TAG, id);
    }

    public void deleteCustomer(String id) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        customerDAOImp.Delete(sqLiteDatabase, TAG, id);
    }

    public int updateUser(User user) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        return userDAOImp.Update(user, sqLiteDatabase, TAG);
    }

    public int updateCustomer(Customer customer) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        return customerDAOImp.Update(customer, sqLiteDatabase, TAG);
    }

    public int countUser() {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        return userDAOImp.Count(sqLiteDatabase, TAG);
    }

    public int countCustomer() {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        return customerDAOImp.Count(sqLiteDatabase, TAG);
    }

    public void addUser(User user) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        userDAOImp.Add(user, sqLiteDatabase, TAG);
    }

    public void addCustomer(Customer customer) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        customerDAOImp.Add(customer, sqLiteDatabase, TAG);
    }

    public List<User> getAllUser() {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        return userDAOImp.getAll(sqLiteDatabase, TAG);
    }

    public List<Customer> getAllCustomer() {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        return customerDAOImp.getAll(sqLiteDatabase, TAG);
    }
}
