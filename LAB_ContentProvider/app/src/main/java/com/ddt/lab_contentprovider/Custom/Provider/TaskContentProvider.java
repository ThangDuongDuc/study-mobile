package com.ddt.lab_contentprovider.Custom.Provider;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

import androidx.annotation.Nullable;

import com.ddt.lab_contentprovider.SQL.MyDatabaseHelper;

import java.util.HashMap;

public class TaskContentProvider extends ContentProvider {
    public static final String PROVIDER_NAME = "com.ddt.lab_contentprovider.Custom.Provider.TaskContentProvider";
    public static final String URL = "content://" + PROVIDER_NAME + "/tasks";
    public static final Uri CONTENT_URI = Uri.parse(URL);

    public static final String ID = "id";
    public static final String NAME = "name";
    public static final String DESCRIPTION = "description";

    public static final int TASKS = 1;
    public static final int TASK_ID = 2;


    private static final UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        uriMatcher.addURI(PROVIDER_NAME, "tasks", TASKS);
        uriMatcher.addURI(PROVIDER_NAME, "tasks/#", TASK_ID);
    }

    private SQLiteDatabase database;

    public TaskContentProvider() {
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        int delCount = 0;
        switch (uriMatcher.match(uri)) {
            case TASKS:
                delCount = database.delete(MyDatabaseHelper.TASK, selection, selectionArgs);
                break;
            default:
                throw new IllegalArgumentException("This is an Unknown URI " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return delCount;
    }

    @Nullable
    @Override
    public String getType(Uri uri) {
        switch (uriMatcher.match(uri)) {
            case TASKS:
                return "com.ddt.lab_contentprovider.tasks";
            default:
                throw new IllegalArgumentException("This is an Unknown URI " + uri);
        }
    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues values) {
        long id = database.insert(MyDatabaseHelper.TASK, null, values);

        if (id > 0) {
            Uri _uri = ContentUris.withAppendedId(CONTENT_URI, id);
            getContext().getContentResolver().notifyChange(_uri, null);

            return _uri;
        }
        throw new SQLException("Insertion Failed for URI :" + uri);
    }

    @Override
    public boolean onCreate() {
        MyDatabaseHelper helper = new MyDatabaseHelper(getContext());
        database = helper.getWritableDatabase();
        return true;
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        Cursor cursor;
        switch (uriMatcher.match(uri)) {
            case TASKS:
                cursor = database.query(MyDatabaseHelper.TASK, MyDatabaseHelper.ALL_COLUMNS,
                        selection, null, null, null, MyDatabaseHelper.COLUMN_TASK_NAME + " ASC");

                break;
            default:
                throw new IllegalArgumentException("This is an Unknown URI " + uri);
        }
        cursor.setNotificationUri(getContext().getContentResolver(), uri);

        return cursor;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        int updCount = 0;
        switch (uriMatcher.match(uri)) {
            case TASKS:
                updCount = database.update(MyDatabaseHelper.TASK, values, selection, selectionArgs);
                break;
            default:
                throw new IllegalArgumentException("This is an Unknown URI " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return updCount;
    }
}