package com.example.sqlite;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class DatabaseHelper extends SQLiteOpenHelper {

    public static String DATABASE_NAME = "user_database";
    private static final int DATABASE_VERSION = 10;
    private static final String TABLE_USER = "users";
    private static final String KEY_ID = "id";
    private static final String KEY_LAB = "lab";
    private static final String KEY_RUT = "rut";
    private static final String KEY_NAME = "name";
    private static final String KEY_DESCR = "descr";
    private static final String KEY_DATE = "date";


    private static final String CREATE_TABLE_STUDENTS = "CREATE TABLE "
            + TABLE_USER + "(" + KEY_ID
            + " INTEGER PRIMARY KEY AUTOINCREMENT," + KEY_LAB + " TEXT, "+ KEY_RUT + " TEXT, "+ KEY_NAME + " TEXT, "+ KEY_DESCR + " TEXT, "+KEY_DATE+ " TEXT );";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);

        Log.d("table", CREATE_TABLE_STUDENTS);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
       db.execSQL(CREATE_TABLE_STUDENTS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion < 5) {
            db.execSQL("ALTER TABLE " + TABLE_USER + " ADD COLUMN new_column_name TEXT;");
        }
    }

    public long addUserDetail(String lab, String rut, String name, String descr) {
        SQLiteDatabase db = this.getWritableDatabase();

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.getDefault());
        String fecha = sdf.format(new Date());

        // Creating content values
        ContentValues values = new ContentValues();
        values.put(KEY_LAB, lab);
        values.put(KEY_RUT, rut);
        values.put(KEY_NAME, name);
        values.put(KEY_DESCR, descr);
        values.put(KEY_DATE, fecha);

        long insert = db.insert(TABLE_USER, null, values);

        return insert;
    }

    @SuppressLint("Range")
    public ArrayList<UserModel> getAllUsers() {
        ArrayList<UserModel> userModelArrayList = new ArrayList<UserModel>();
        String selectQuery = "SELECT  * FROM " + TABLE_USER;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);
        // looping through all rows and adding to list
        if (c.moveToFirst()) {
            do {
                UserModel userModel = new UserModel();
                userModel.setId(c.getInt(c.getColumnIndex(KEY_ID)));
                userModel.setLab(c.getString(c.getColumnIndex(KEY_LAB)));
                userModel.setRut(c.getString(c.getColumnIndex(KEY_RUT)));
                userModel.setName(c.getString(c.getColumnIndex(KEY_NAME)));
                userModel.setDescr(c.getString(c.getColumnIndex(KEY_DESCR)));
                userModel.setDate(c.getString(c.getColumnIndex(KEY_DATE)));

                userModelArrayList.add(userModel);
            } while (c.moveToNext());
         }
        return userModelArrayList;
    }

    public int updateDescription(int id, String descr) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_DESCR, descr);

        return db.update(TABLE_USER, values, KEY_ID + " = ?", new String[]{String.valueOf(id)});
    }

    public void deleteUSer(int id) {

        // delete row in students table based on id
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_USER, KEY_ID + " = ?",
                new String[]{String.valueOf(id)});
    }

}

