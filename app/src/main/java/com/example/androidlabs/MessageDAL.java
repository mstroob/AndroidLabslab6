package com.example.androidlabs;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;

public class MessageDAL {

    MessageDAL(){}

    void addMessage(Context context, Message message){
            String sent = "false";
            MessageSQLiteDatabaseHelper dbHelper = new MessageSQLiteDatabaseHelper(context);
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(MessageSQLiteDatabaseHelper.COL_MESSAGE, message.getMessage());
            if (message.isSent()){sent = "true";}
            values.put(MessageSQLiteDatabaseHelper.COL_ISSENT, sent);

            message.setId(db.insert(MessageSQLiteDatabaseHelper.TABLE_NAME, MessageSQLiteDatabaseHelper.COL_MESSAGE, values));
            dbHelper.close();
    }


    boolean removeMessage(Context context, Message message){
        MessageSQLiteDatabaseHelper dbHelper = new MessageSQLiteDatabaseHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();


        String[] selectionArgs = new String[]{Long.toString(message.getId())};
        String selection = MessageSQLiteDatabaseHelper.COL_ID+" = ?";


        db.delete( MessageSQLiteDatabaseHelper.TABLE_NAME, selection,selectionArgs);
        dbHelper.close();
        return true;
    }

    ArrayList<Message> getALl(Context context){
        ArrayList<Message> messagesList = new ArrayList<Message>();
        MessageSQLiteDatabaseHelper dbHelper = new MessageSQLiteDatabaseHelper(context);
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String[] selectionArgs = new String[]{"impossibleSent"};
        String selection = MessageSQLiteDatabaseHelper.COL_ISSENT+" != ?";
        Cursor c = db.query(true, MessageSQLiteDatabaseHelper.TABLE_NAME, MessageSQLiteDatabaseHelper.ALL_COLUMNS, selection, selectionArgs, null,null,null,null);
        System.out.println(c.getCount());
        if (c.getCount() != 0) {
            c.moveToFirst();
            for(int i=0; i<c.getCount(); i++){
                boolean sent = false;
                Message message = new Message();
                message.setId(c.getInt( c.getColumnIndex(MessageSQLiteDatabaseHelper.COL_ID)));
                message.setMessage(c.getString( c.getColumnIndex(MessageSQLiteDatabaseHelper.COL_MESSAGE)));
                if (c.getString( c.getColumnIndex(MessageSQLiteDatabaseHelper.COL_ISSENT)).equals("true")) sent = true;
                message.setSent(sent);
                messagesList.add(message);
                c.moveToNext();
            }
        }
        c.close();
        dbHelper.close();
        return (messagesList);
    }

    String printCursor(Context context){
        ArrayList<Message> messagesList = new ArrayList<Message>();
        MessageSQLiteDatabaseHelper dbHelper = new MessageSQLiteDatabaseHelper(context);
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String[] selectionArgs = new String[]{"impossibleSent"};
        String selection = MessageSQLiteDatabaseHelper.COL_ISSENT+" != ?";
        Cursor c = db.query(true, MessageSQLiteDatabaseHelper.TABLE_NAME, MessageSQLiteDatabaseHelper.ALL_COLUMNS, selection, selectionArgs, null,null,null,null);

        Log.e("Version", Integer.toString(db.getVersion()) );
        Log.e("ColumnNumber", Integer.toString(c.getColumnCount()) );
        Log.e("ColumnName", Arrays.toString(c.getColumnNames()) );
        Log.e("NumberofRow", Integer.toString(c.getCount()) );

        if (c.getCount() != 0) {
            c.moveToFirst();
            for(int i=0; i<c.getCount(); i++) {
                Log.e("ColumnName", c.getInt( c.getColumnIndex(MessageSQLiteDatabaseHelper.COL_ID))+c.getString(c.getColumnIndex(MessageSQLiteDatabaseHelper.COL_MESSAGE))+c.getString( c.getColumnIndex(MessageSQLiteDatabaseHelper.COL_ISSENT)));
                c.moveToNext();
            }
        }
        return "c";
    }


}
