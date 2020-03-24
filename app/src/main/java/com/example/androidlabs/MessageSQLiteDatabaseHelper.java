package com.example.androidlabs;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class MessageSQLiteDatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "MESSAGES";
    private static final int VERSION_NUMBER = 1;
    final static String TABLE_NAME = "MESSAGES_TABLES";
    final static String COL_MESSAGE = "MESSAGE";
    final static String COL_ISSENT = "ISSENT";

    final static String COL_ID = "_id";
    final static String[] ALL_COLUMNS = {COL_ID,COL_MESSAGE,COL_ISSENT};


    /**
     * Instantiates a new Ndisqliteopenhelper.
     *
     * @param context the context
     */
    MessageSQLiteDatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, VERSION_NUMBER);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL( "CREATE TABLE " + TABLE_NAME + "( "+COL_ID+" INTEGER PRIMARY KEY AUTOINCREMENT, "+COL_MESSAGE+" text, "+COL_ISSENT+" text);" );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

}
