package com.example.erick.ta_projek_erick;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DbHandler extends SQLiteOpenHelper {
    // Semua konstant karena statis dan final (Db = Database)

    public DbHandler(Context context) {
        super(context, "SMHotel.db", null, 16);
    }


    //creating table
    @Override
    public void onCreate(SQLiteDatabase db) {
        // query membuat table login
        db.execSQL("CREATE TABLE user "  + "(id INTEGER PRIMARY KEY, username TEXT, password TEXT)");
        //query table kamar
        db.execSQL("CREATE TABLE kamar " + "(idkamar INTEGER PRIMARY KEY, nokamar TEXT, tipekamar TEXT, lantai TEXT, biaya INTEGER, status TEXT)");
//        query table reservasi
        db.execSQL("CREATE TABLE reservasi " + "(idreservasi INTEGER PRIMARY KEY, namacust TEXT, nohp TEXT, tglcekin TEXT, tglcekout TEXT, tipekamar INTEGER, nokamar TEXT, lantai TEXT, hari INTEGER, total INTEGER, status TEXT, FOREIGN KEY(tipekamar) REFERENCES kamar(idkamar))");
    }

    //Upgrading the Db jika aplikasi dibuild ulang
    @Override
    public  void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
        db.execSQL("DROP TABLE IF EXISTS user");
        db.execSQL("DROP TABLE IF EXISTS kamar");
        db.execSQL("DROP TABLE IF EXISTS reservasi");
        onCreate(db);

    }







}
