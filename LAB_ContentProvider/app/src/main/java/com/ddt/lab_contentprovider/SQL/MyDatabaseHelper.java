package com.ddt.lab_contentprovider.SQL;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import com.ddt.lab_contentprovider.Model.Task;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class MyDatabaseHelper extends SQLiteOpenHelper {
    private static final String TAG = "SQLite";
    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "TaskContentProvider";

    // Table name: Task.
    public static final String TASK = "Task";

    public static final String COLUMN_TASK_ID ="Id";
    public static final String COLUMN_TASK_NAME ="Name";
    public static final String COLUMN_TASK_DESCRIPTION = "Description";
    public static final String[] ALL_COLUMNS =
            {COLUMN_TASK_ID, COLUMN_TASK_NAME, COLUMN_TASK_DESCRIPTION};

    public MyDatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        Log.i(TAG, "MyDatabaseHelper.onCreate ... ");
        // Script to create table.
        String script = "CREATE TABLE IF NOT EXISTS " + TASK + "("
                + COLUMN_TASK_ID + " TEXT PRIMARY KEY," + COLUMN_TASK_NAME + " TEXT,"
                + COLUMN_TASK_DESCRIPTION + " TEXT" + ")";
        // Execute script.
        sqLiteDatabase.execSQL(script);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        Log.i(TAG, "MyDatabaseHelper.onUpgrade ... ");
        // Drop older table if existed
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TASK);

        // Create tables again
        onCreate(sqLiteDatabase);
    }

    public void createDefaultTaskIfNeed()  {
        int count = this.count();
        if(count == 0 ) {
            Task task1 = new Task(UUID.randomUUID(), "Task 01", "Lam quen voi Android Studio");
            Task task2 = new Task(UUID.randomUUID(), "Task 02", "Layout va View trong Android");

           this.save(task1);
           this.save(task2);
        }
    }

    public int count() {
        Log.i(TAG, "MyDatabaseHelper.getTaskCount ... " );

        String countQuery = "SELECT * FROM " + TASK;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);

        int count = cursor.getCount();

        cursor.close();

        // return count
        return count;
    }

    public void save(Task task) {
        Log.i(TAG, "MyDatabaseHelper.addTask ... " + task.getName());
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_TASK_ID, task.getId().toString());
        values.put(COLUMN_TASK_NAME, task.getName());
        values.put(COLUMN_TASK_DESCRIPTION, task.getDescription());

        sqLiteDatabase.insert(TASK, null, values);

        sqLiteDatabase.close();
    }

    public int update(Task task) {
        Log.i(TAG, "MyDatabaseHelper.updateTask ... "  + task.getName());

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_TASK_ID, task.getId().toString());
        values.put(COLUMN_TASK_NAME, task.getName());
        values.put(COLUMN_TASK_DESCRIPTION, task.getDescription());

        // updating row
        return db.update(TASK, values, COLUMN_TASK_ID + " = ?",
                new String[]{String.valueOf(task.getId())});
    }

    public void delete(String Id) {
        Log.i(TAG, "MyDatabaseHelper.deleteTask ... " + Id);

        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TASK, COLUMN_TASK_ID + " = ?",
                new String[] { String.valueOf(Id) });
        db.close();
    }

    public ArrayList<Task> getAll() {
        Log.i(TAG, "MyDatabaseHelper.getAllTask ... " );

        ArrayList<Task> taskList = new ArrayList<Task>();
        // Select All Query
        String selectQuery = "SELECT * FROM " + TASK;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Task task = new Task();
                task.setId(UUID.fromString(cursor.getString(0)));
                task.setName(cursor.getString(1));
                task.setDescription(cursor.getString(2));
                // Adding note to list
                taskList.add(task);
            } while (cursor.moveToNext());
        }

        // return note list
        return taskList;
    }
}
