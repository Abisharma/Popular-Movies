package com.example.ekta.popularmovies.Utilities;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by Ekta on 06-10-2016.
 */
public class DataSource {
    Cursor cursor;
    // Database fields
    private SQLiteDatabase database;
    private DbHelper dbHelper;
    private String[] allColumns = { DbHelper.COLUMN_ID,
            DbHelper.TITLE,
            DbHelper.OVERVIEW,DbHelper.RELEASEDATE };

    public DataSource(Context context) {
        dbHelper = new DbHelper(context);
        open();
    }

    public void open() throws SQLException {
        // database = this.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }


}

