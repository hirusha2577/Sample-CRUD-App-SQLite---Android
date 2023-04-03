package com.example.testsqlite2;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.view.View;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;


public class DBHelper extends SQLiteOpenHelper {

    public DBHelper(Context context) {
        super(context, "Shop",null,1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        String SQL = "CREATE TABLE employee (" +
                "id INTEGER PRIMARY KEY," +
                "name TEXT," +
                "email TEXT," +
                "gender TEXT)";

        sqLiteDatabase.execSQL(SQL);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public long addEmployee(String name, String email, String gender){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("name",name);
        contentValues.put("email",email);
        contentValues.put("gender",gender);
        long newID = db.insert("employee",null,contentValues);
        if(db != null){
            db.close();
        }
        return newID;
    }

    public Cursor readEmployee(String name){
        SQLiteDatabase db = getReadableDatabase();
        String [] projection = {
                "id",
                "name",
                "email",
                "gender"
        };
        if(name.equals("")) {
            String shortOrder = "id DESC";
            Cursor cursor = db.query(
                    "employee",
                    projection,
                    null,
                    null,
                    null,
                    null,
                    shortOrder
            );
            return cursor;
        }else{
        String selection = "name LIKE ?";
        String [] selectionArgs = {name};
        String shortOrder = "id DESC";
        Cursor cursor = db.query(
                "employee",
                projection,
                selection,
                selectionArgs,
                null,
                null,
                shortOrder
        );
        return cursor;
        }

    }

    public boolean deleteEmployee(String id){
        SQLiteDatabase db = getReadableDatabase();
        String selection = "id LIKE ?";
        String [] selectionArgs = {id};
        return  db.delete("employee",selection,selectionArgs)>0;
    }

    public long updateEmployee(String id, String name, String email, String gender){
        SQLiteDatabase db = getReadableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("name",name);
        contentValues.put("email",email);
        contentValues.put("gender",gender);

        String selection = "id LIKE ?";
        String [] selectionArgs = {id};

        int count = db.update(
                "employee",
                contentValues,
                selection,
                selectionArgs
        );
       return count;
    }
}
