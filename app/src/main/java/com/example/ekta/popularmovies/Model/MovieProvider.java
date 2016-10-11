package com.example.ekta.popularmovies.Model;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.support.annotation.Nullable;

import com.example.ekta.popularmovies.Database.DbHelper;

/**
 * Created by Ekta on 06-10-2016.
 */
public class MovieProvider extends ContentProvider {

    static final String PROVIDER_NAME = "com.example.ekta.popularmovies/popularmovies";
    static final String URL = "content://" + PROVIDER_NAME ;
    public static final Uri CONTENT_URI = Uri.parse(URL);
    DbHelper dbHelper;
    static final String id = "id";

    SQLiteDatabase db;
    @Override
    public boolean onCreate() {
        Context context = getContext();
        dbHelper = new DbHelper(context);

        db = dbHelper.getWritableDatabase();
        if (db != null) {
            return true;
        }
        return false;
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        db = dbHelper.getWritableDatabase();
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
        qb.setTables(dbHelper.TABLE_MOVIES);

        if (sortOrder == null || sortOrder == "") {
            sortOrder = id;
        }

        Cursor c = qb.query(db, projection, selection, selectionArgs, null,
                null, sortOrder);
        c.setNotificationUri(getContext().getContentResolver(), uri);
        return c;
    }

    @Nullable
    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues values) {
        db = dbHelper.getWritableDatabase();
        long rowID = db.insert(dbHelper.TABLE_MOVIES, "", values);
        if (rowID > 0) {
            Uri _uri = ContentUris.withAppendedId(CONTENT_URI, rowID);
            getContext().getContentResolver().notifyChange(_uri, null);
            return _uri;
        }
        throw new SQLException("Failed to add a record into " + uri);

    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        db = dbHelper.getWritableDatabase();
        int deleteCount = db.delete(dbHelper.TABLE_MOVIES, selection, selectionArgs);
        getContext().getContentResolver().notifyChange(uri, null);
        return deleteCount;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        db = dbHelper.getWritableDatabase();
        int count = 0;


        count = db.update(dbHelper.TABLE_MOVIES, values, selection, selectionArgs);


        getContext().getContentResolver().notifyChange(uri, null);
        return count;
    }
}

