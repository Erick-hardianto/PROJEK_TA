package com.example.erick.ta_projek_erick;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class DBController {
    DbHandler dbSqlite;
    private Context context;

    public DBController(Context context) {
        dbSqlite = new DbHandler(context);
        this.context = context;
    }

    //membuat kolom
    private static final String User_name = "username";
    private static final String User_password = "password";


    public String Reservasi(String nama, String nohp, String tglcekin, String tglcekout, String tipekamar, String nokamar, String lantai, Integer hari) {

        try {
            SQLiteDatabase db = dbSqlite.getWritableDatabase();
            Integer biayakamar = 0;
            String idkamar = "";

            SQLiteDatabase db1 = dbSqlite.getReadableDatabase();

            String selectQuery = "SELECT idkamar FROM kamar WHERE tipekamar = '" + tipekamar + "'";
            Cursor cursor = db1.rawQuery(selectQuery, null);
            // Perulangan sejumlah data yang ada dan tambahkan ke list.
            if (cursor.moveToFirst()) {
                do {
                    idkamar = cursor.getString(0);
                } while (cursor.moveToNext());
            }

            String selectQuery1 = "SELECT biaya FROM kamar WHERE idkamar = '" +idkamar + "'";
            Cursor cursor2 = db1.rawQuery(selectQuery1, null);
            // Perulangan sejumlah data yang ada dan tambahkan ke list.
            if (cursor2.moveToFirst()) {
                do {
                    biayakamar = cursor2.getInt(0);
                } while (cursor2.moveToNext());
            }

            //menghitung total biaya
            Integer total = biayakamar * hari;

            ContentValues contentValues = new ContentValues();
            contentValues.put("namacust", nama);
            contentValues.put("nohp", nohp);
            contentValues.put("tglcekin", tglcekin);
            contentValues.put("tglcekout", tglcekout);
            contentValues.put("tipekamar", idkamar);
            contentValues.put("nokamar", nokamar);
            contentValues.put("lantai", lantai);
            contentValues.put("hari", hari);
            contentValues.put("total", total);
            contentValues.put("status", "Cekin");

            db.insert("reservasi", null, contentValues);

            db.execSQL("UPDATE kamar set status = 'Terisi' WHERE idkamar = '"+idkamar+"'");

            db.close();
            return  "sukses";
        } catch (Exception e) {
           return e.getMessage();
        }


    }

    public ArrayList<HashMap<String, String>> showAllDataReservasiCekin(){
        ArrayList<HashMap<String, String>> data;
        data = new ArrayList<HashMap<String, String>>();
        String selectQuery = "SELECT idreservasi, namacust, nohp, tglcekin, tglcekout, kamar.tipekamar, reservasi.nokamar, reservasi.lantai, kamar.biaya, hari,total, reservasi.status  FROM reservasi INNER JOIN kamar ON reservasi.tipekamar = kamar.idkamar WHERE reservasi.status = 'Cekin' ORDER BY idreservasi DESC";
        SQLiteDatabase database = dbSqlite.getWritableDatabase();
        Cursor cursor = database.rawQuery(selectQuery, null);
        if(cursor.moveToFirst()) {
            do{
                HashMap<String, String> map = new HashMap<String , String>();
                map.put("idreservasi", cursor.getString(0));
                map.put("namacust", cursor.getString(1));
                map.put("nohp", cursor.getString(2));
                map.put("tglcekin", cursor.getString(3));
                map.put("tglcekout", cursor.getString(4));
                map.put("kamar.tipekamar", cursor.getString(5));
                map.put("reservasi.nokamar", cursor.getString(6));
                map.put("reservasi.lantai", cursor.getString(7));
                map.put("kamar.biaya", cursor.getString(8));
                map.put("hari", cursor.getString(9));
                map.put("total", cursor.getString(10));
                map.put("reservasi.status", cursor.getString(11));
                data.add(map);
            } while (cursor.moveToNext());
        }
        database.close();
        return data;
    }


    public ArrayList<HashMap<String, String>> showAllSemuaDataReservasi(){
        ArrayList<HashMap<String, String>> data;
        data = new ArrayList<HashMap<String, String>>();
        String selectQuery = "SELECT idreservasi, namacust, nohp, tglcekin, tglcekout, kamar.tipekamar, reservasi.nokamar, reservasi.lantai, kamar.biaya, hari,total, reservasi.status  FROM reservasi INNER JOIN kamar ON reservasi.tipekamar = kamar.idkamar ORDER BY idreservasi DESC";
        SQLiteDatabase database = dbSqlite.getWritableDatabase();
        Cursor cursor = database.rawQuery(selectQuery, null);
        if(cursor.moveToFirst()) {
            do{
                HashMap<String, String> map = new HashMap<String , String>();
                map.put("idreservasi", cursor.getString(0));
                map.put("namacust", cursor.getString(1));
                map.put("nohp", cursor.getString(2));
                map.put("tglcekin", cursor.getString(3));
                map.put("tglcekout", cursor.getString(4));
                map.put("kamar.tipekamar", cursor.getString(5));
                map.put("reservasi.nokamar", cursor.getString(6));
                map.put("reservasi.lantai", cursor.getString(7));
                map.put("kamar.biaya", cursor.getString(8));
                map.put("hari", cursor.getString(9));
                map.put("total", cursor.getString(10));
                map.put("reservasi.status", cursor.getString(11));
                data.add(map);
            } while (cursor.moveToNext());
        }
        database.close();
        return data;
    }

    public ArrayList<HashMap<String, String>> showAllDataReservasiCekout(){
        ArrayList<HashMap<String, String>> data;
        data = new ArrayList<HashMap<String, String>>();
        String selectQuery = "SELECT idreservasi, namacust, nohp, tglcekin, tglcekout, kamar.tipekamar, reservasi.nokamar, reservasi.lantai, kamar.biaya, hari,total, reservasi.status  FROM reservasi INNER JOIN kamar ON reservasi.tipekamar = kamar.idkamar WHERE reservasi.status = 'Cekout' ORDER BY idreservasi DESC";
        SQLiteDatabase database = dbSqlite.getWritableDatabase();
        Cursor cursor = database.rawQuery(selectQuery, null);
        if(cursor.moveToFirst()) {
            do{
                HashMap<String, String> map = new HashMap<String , String>();
                map.put("idreservasi", cursor.getString(0));
                map.put("namacust", cursor.getString(1));
                map.put("nohp", cursor.getString(2));
                map.put("tglcekin", cursor.getString(3));
                map.put("tglcekout", cursor.getString(4));
                map.put("kamar.tipekamar", cursor.getString(5));
                map.put("reservasi.nokamar", cursor.getString(6));
                map.put("reservasi.lantai", cursor.getString(7));
                map.put("kamar.biaya", cursor.getString(8));
                map.put("hari", cursor.getString(9));
                map.put("total", cursor.getString(10));
                map.put("reservasi.status", cursor.getString(11));
                data.add(map);
            } while (cursor.moveToNext());
        }
        database.close();
        return data;
    }

    public ArrayList<HashMap<String, String>> nokamarauto(){
        ArrayList<HashMap<String, String>> data;
        data = new ArrayList<HashMap<String, String>>();
        String selectQuery = "select nokamar+1 from kamar where nokamar in(select max(nokamar) from kamar)";
        SQLiteDatabase database = dbSqlite.getWritableDatabase();
        Cursor cursor = database.rawQuery(selectQuery, null);
        if(cursor.moveToFirst()) {
            do{
                HashMap<String, String> map = new HashMap<String , String>();
                map.put("nokamar+1", cursor.getString(0));
                data.add(map);
            } while (cursor.moveToNext());
        }
        database.close();
        return data;
    }

    public ArrayList<HashMap<String, String>> konfirmasiTagihan(){
        ArrayList<HashMap<String, String>> data;
        data = new ArrayList<HashMap<String, String>>();
        String selectQuery = "SELECT idreservasi, namacust, tglcekin, tglcekout, kamar.tipekamar, reservasi.nokamar, reservasi.lantai, kamar.biaya, hari,total FROM reservasi INNER JOIN kamar ON reservasi.tipekamar = kamar.idkamar WHERE idreservasi IN(SELECT MAX(idreservasi)  FROM reservasi)";
        SQLiteDatabase database = dbSqlite.getWritableDatabase();
        Cursor cursor = database.rawQuery(selectQuery, null);
        if(cursor.moveToFirst()) {
            do{
                HashMap<String, String> map = new HashMap<String , String>();
                map.put("idreservasi", cursor.getString(0));
                map.put("namacust", cursor.getString(1));
                map.put("tglcekin", cursor.getString(2));
                map.put("tglcekout", cursor.getString(3));
                map.put("kamar.tipekamar", cursor.getString(4));
                map.put("reservasi.nokamar", cursor.getString(5));
                map.put("reservasi.lantai", cursor.getString(6));
                map.put("kamar.biaya", cursor.getString(7));
                map.put("hari", cursor.getString(8));
                map.put("total", cursor.getString(9));
                data.add(map);
            } while (cursor.moveToNext());
        }
        database.close();
        return data;
    }

    public ArrayList<HashMap<String, String>> jumlahKamartersedia(){
        ArrayList<HashMap<String, String>> data;
        data = new ArrayList<HashMap<String, String>>();
        String selectQuery = "SELECT COUNT(idkamar) FROM kamar WHERE status = 'Tersedia'";
        SQLiteDatabase database = dbSqlite.getWritableDatabase();
        Cursor cursor = database.rawQuery(selectQuery, null);
        if(cursor.moveToFirst()) {
            do{
                HashMap<String, String> map = new HashMap<String , String>();
                map.put("COUNT(idkamar)", cursor.getString(0));
                data.add(map);
            } while (cursor.moveToNext());
        }
        database.close();
        return data;
    }

    public ArrayList<HashMap<String, String>> jumlahKamarterisi(){
        ArrayList<HashMap<String, String>> data;
        data = new ArrayList<HashMap<String, String>>();
        String selectQuery = "SELECT COUNT(idkamar) FROM kamar WHERE status = 'Terisi' AND idkamar != 1";
        SQLiteDatabase database = dbSqlite.getWritableDatabase();
        Cursor cursor = database.rawQuery(selectQuery, null);
        if(cursor.moveToFirst()) {
            do{
                HashMap<String, String> map = new HashMap<String , String>();
                map.put("COUNT(idkamar)", cursor.getString(0));
                data.add(map);
            } while (cursor.moveToNext());
        }
        database.close();
        return data;
    }

    public ArrayList<HashMap<String, String>> jumlahKamarTdktersedia(){
        ArrayList<HashMap<String, String>> data;
        data = new ArrayList<HashMap<String, String>>();
        String selectQuery = "SELECT COUNT(idkamar) FROM kamar WHERE status = 'Tidak Tersedia'";
        SQLiteDatabase database = dbSqlite.getWritableDatabase();
        Cursor cursor = database.rawQuery(selectQuery, null);
        if(cursor.moveToFirst()) {
            do{
                HashMap<String, String> map = new HashMap<String , String>();
                map.put("COUNT(idkamar)", cursor.getString(0));
                data.add(map);
            } while (cursor.moveToNext());
        }
        database.close();
        return data;
    }
    public ArrayList<HashMap<String, String>> jumlahsemuaKamar(){
        ArrayList<HashMap<String, String>> data;
        data = new ArrayList<HashMap<String, String>>();
        String selectQuery = "SELECT COUNT(idkamar) FROM kamar WHERE idkamar != 1";
        SQLiteDatabase database = dbSqlite.getWritableDatabase();
        Cursor cursor = database.rawQuery(selectQuery, null);
        if(cursor.moveToFirst()) {
            do{
                HashMap<String, String> map = new HashMap<String , String>();
                map.put("COUNT(idkamar)", cursor.getString(0));
                data.add(map);
            } while (cursor.moveToNext());
        }
        database.close();
        return data;
    }

    public ArrayList<HashMap<String, String>> jumlahCekin(){
        ArrayList<HashMap<String, String>> data;
        data = new ArrayList<HashMap<String, String>>();
        String selectQuery = "SELECT COUNT(idreservasi) FROM reservasi WHERE status = 'Cekin'";
        SQLiteDatabase database = dbSqlite.getWritableDatabase();
        Cursor cursor = database.rawQuery(selectQuery, null);
        if(cursor.moveToFirst()) {
            do{
                HashMap<String, String> map = new HashMap<String , String>();
                map.put("COUNT(idreservasi)", cursor.getString(0));
                data.add(map);
            } while (cursor.moveToNext());
        }
        database.close();
        return data;
    }

    public ArrayList<HashMap<String, String>> jumlahCekout(){
        ArrayList<HashMap<String, String>> data;
        data = new ArrayList<HashMap<String, String>>();
        String selectQuery = "SELECT COUNT(idreservasi) FROM reservasi WHERE status = 'Cekout'";
        SQLiteDatabase database = dbSqlite.getWritableDatabase();
        Cursor cursor = database.rawQuery(selectQuery, null);
        if(cursor.moveToFirst()) {
            do{
                HashMap<String, String> map = new HashMap<String , String>();
                map.put("COUNT(idreservasi)", cursor.getString(0));
                data.add(map);
            } while (cursor.moveToNext());
        }
        database.close();
        return data;
    }

    public ArrayList<HashMap<String, String>> jumlahSemuaTamu(){
        ArrayList<HashMap<String, String>> data;
        data = new ArrayList<HashMap<String, String>>();
        String selectQuery = "SELECT COUNT(idreservasi) FROM reservasi";
        SQLiteDatabase database = dbSqlite.getWritableDatabase();
        Cursor cursor = database.rawQuery(selectQuery, null);
        if(cursor.moveToFirst()) {
            do{
                HashMap<String, String> map = new HashMap<String , String>();
                map.put("COUNT(idreservasi)", cursor.getString(0));
                data.add(map);
            } while (cursor.moveToNext());
        }
        database.close();
        return data;
    }

    public ArrayList<HashMap<String, String>> showAllDataReservasiById(int id){
        ArrayList<HashMap<String, String>> data;
        data = new ArrayList<HashMap<String, String>>();
        String selectQuery = "SELECT idreservasi, namacust, nohp, tglcekin, tglcekout, kamar.tipekamar, reservasi.nokamar, reservasi.lantai, kamar.biaya, hari, total, reservasi.status  FROM reservasi INNER JOIN kamar ON reservasi.tipekamar = kamar.idkamar WHERE idreservasi = '"+id+"' ORDER BY idreservasi DESC";
        SQLiteDatabase database = dbSqlite.getWritableDatabase();
        Cursor cursor = database.rawQuery(selectQuery, null);
        if(cursor.moveToFirst()) {
            do{
                HashMap<String, String> map = new HashMap<String , String>();
                map.put("idreservasi", cursor.getString(0));
                map.put("namacust", cursor.getString(1));
                map.put("nohp", cursor.getString(2));
                map.put("tglcekin", cursor.getString(3));
                map.put("tglcekout", cursor.getString(4));
                map.put("kamar.tipekamar", cursor.getString(5));
                map.put("reservasi.nokamar", cursor.getString(6));
                map.put("reservasi.lantai", cursor.getString(7));
                map.put("kamar.biaya", cursor.getString(8));
                map.put("hari", cursor.getString(9));
                map.put("total", cursor.getString(10));
                map.put("reservasi.status", cursor.getString(11));
                data.add(map);
            } while (cursor.moveToNext());
        }
        database.close();
        return data;
    }

    //Menambahkan user baru sebagai objek dari kelas user
    public void addUser(User usr) {
        // mendapatkan instance db untuk dinulis user
        SQLiteDatabase db = dbSqlite.getWritableDatabase();
        ContentValues cv = new ContentValues();

        // cv.put(User_id,usr.getId());
        cv.put(User_name, usr.getUname());
        cv.put(User_password, usr.getPssword());

        //insert row data ke table
        db.insert("user", null, cv);

        //close the database to avoid any leak
        db.close();
    }

    //Menambahkan pilih tipe kamar awal load
    public void addTipekamar(TipeKamar tipeKamar) {
        // mendapatkan instance db untuk dinulis user
        SQLiteDatabase db = dbSqlite.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put("nokamar", tipeKamar.getNokamar());
        cv.put("tipekamar", tipeKamar.getTipekamar());
        cv.put("lantai", tipeKamar.getLantai());
        cv.put("biaya", tipeKamar.getBiaya());
        cv.put("status", tipeKamar.getStatus());
        //insert row data ke table
        db.insert("kamar", null, cv);

        //close the database to avoid any leak
        db.close();
    }


    //mengecek apakah user ada atau tidak
    public int cekUsername(User us) {
        int id = -1;
        SQLiteDatabase db = dbSqlite.getReadableDatabase();
        //mendapatkan data Id berdasarkan username dan password
        Cursor cursor = db.rawQuery("SELECT id FROM user WHERE username=? AND password=?", new String[]{us.getUname(), us.getPssword()});
        if (cursor.getCount() > 0) {
            //pindah ke cursor index paling pertama
            cursor.moveToFirst();
            //menyimpan id nya
            id = cursor.getInt(0);
            cursor.close();
        }
        return id;
    }
    //1. Kelola kamar/
    public void saveDataKamar(HashMap<String, String> queryValues) {
        try {
            SQLiteDatabase database = dbSqlite.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put("nokamar", queryValues.get("nokamar"));
            values.put("tipekamar", queryValues.get("tipekamar"));
            values.put("lantai", queryValues.get("lantai"));
            values.put("biaya", queryValues.get("biaya"));
            values.put("status", queryValues.get("status"));
            database.insert("kamar", null, values);
            database.close();
        }
        catch (Exception ex){
            Log.d("GETDATA", ex.getMessage());
        }
    }



    public int updateReservasi( HashMap<String, String> queryValues) {
        SQLiteDatabase database = dbSqlite.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("namacust", queryValues.get("namacust"));
        values.put("nohp", queryValues.get("nohp"));
        return database.update("reservasi", values, "idreservasi" + " = ?", new String[] { queryValues.get("idreservasi") });
    }



    public int updateDataKamar( HashMap<String, String> queryValues) {
        SQLiteDatabase database = dbSqlite.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("nokamar", queryValues.get("nokamar"));
        values.put("tipekamar", queryValues.get("tipekamar"));
        values.put("lantai", queryValues.get("lantai"));
        values.put("biaya", queryValues.get("biaya"));
        values.put("status", queryValues.get("status"));
        return database.update("kamar", values, "idkamar" + " = ?", new String[] { queryValues.get("idkamar") });
    }
    public void hapusDataKamar(int id){
        Log.d("GETDATA", "delete");
        SQLiteDatabase database = dbSqlite.getWritableDatabase();
        String deleteQuery = "DELETE FROM kamar where idkamar= '"+ id +"'";
        Log.d("query", deleteQuery);
        database.execSQL(deleteQuery);
    }

    public void cekout(int id){
        String idkamar = "";
        String idreservasi = "";

        SQLiteDatabase db1 = dbSqlite.getReadableDatabase();

        String selectQuery = "SELECT idreservasi, tipekamar FROM reservasi WHERE idreservasi = '"+id+"'";
        Cursor cursor = db1.rawQuery(selectQuery, null);
        // Perulangan sejumlah data yang ada dan tambahkan ke list.
        if (cursor.moveToFirst()) {
            do {
                idreservasi = cursor.getString(0);
                idkamar = cursor.getString(1);
            } while (cursor.moveToNext());
        }


        Log.d("GETDATA", "cekout");
        SQLiteDatabase database = dbSqlite.getWritableDatabase();
        String updateStatus = "UPDATE reservasi set status = 'Cekout' where idreservasi= '"+ idreservasi +"'";
        Log.d("query", updateStatus);
        database.execSQL(updateStatus);

        Log.d("GETDATA", "cekout");
        SQLiteDatabase database1 = dbSqlite.getWritableDatabase();
        String updateKamar = "UPDATE kamar set status = 'Tersedia' where idkamar= '"+ idkamar +"'";
        Log.d("query", updateKamar);
        database1.execSQL(updateKamar);
    }

    //ditaro di login.java untuk load masukkan pilihan tipekamar dispinner reservasi (ketika upgrade)
    public void hapusDataTipe(){
        Log.d("GETDATA", "delete");
        SQLiteDatabase database = dbSqlite.getWritableDatabase();
        String deleteQuery = "DELETE FROM kamar where tipekamar= 'Pilih Tipe Kamar' AND idkamar != 1";
        Log.d("query", deleteQuery);
        database.execSQL(deleteQuery);
    }

    //ditaro di login.java untuk load masukkan pilihan tipekamar dispinner reservasi
    public void hapusDataUser(){
        Log.d("GETDATA", "delete");
        SQLiteDatabase database = dbSqlite.getWritableDatabase();
        String deleteQuery = "DELETE FROM user where username != 'erick'";
        Log.d("query", deleteQuery);
        database.execSQL(deleteQuery);
    }

    public ArrayList<HashMap<String, String>> showAllDataKamar(){
        ArrayList<HashMap<String, String>> data;
        data = new ArrayList<HashMap<String, String>>();
        String selectQuery = "SELECT * FROM kamar  WHERE idkamar != 1 AND status = 'Tersedia' ORDER BY idkamar DESC";
        SQLiteDatabase database = dbSqlite.getWritableDatabase();
        Cursor cursor = database.rawQuery(selectQuery, null);
        if(cursor.moveToFirst()) {
            do{
                HashMap<String, String> map = new HashMap<String , String>();
                map.put("idkamar", cursor.getString(0));
                map.put("nokamar", cursor.getString(1));
                map.put("tipekamar", cursor.getString(2));
                map.put("lantai", cursor.getString(3));
                map.put("biaya", cursor.getString(4));
                map.put("status", cursor.getString(5));
                data.add(map);
            } while (cursor.moveToNext());
        }
        database.close();
        return data;
    }

    public ArrayList<HashMap<String, String>> showAllDataSemuaKamar(){
        ArrayList<HashMap<String, String>> data;
        data = new ArrayList<HashMap<String, String>>();
        String selectQuery = "SELECT * FROM kamar  WHERE  idkamar != 1 ORDER BY idkamar DESC";
        SQLiteDatabase database = dbSqlite.getWritableDatabase();
        Cursor cursor = database.rawQuery(selectQuery, null);
        if(cursor.moveToFirst()) {
            do{
                HashMap<String, String> map = new HashMap<String , String>();
                map.put("idkamar", cursor.getString(0));
                map.put("nokamar", cursor.getString(1));
                map.put("tipekamar", cursor.getString(2));
                map.put("lantai", cursor.getString(3));
                map.put("biaya", cursor.getString(4));
                map.put("status", cursor.getString(5));
                data.add(map);
            } while (cursor.moveToNext());
        }
        database.close();
        return data;
    }


    public ArrayList<HashMap<String, String>> showAllDataKamarTdk(){
        ArrayList<HashMap<String, String>> data;
        data = new ArrayList<HashMap<String, String>>();
        String selectQuery = "SELECT * FROM kamar  WHERE  status = 'Tidak Tersedia' ORDER BY idkamar DESC";
        SQLiteDatabase database = dbSqlite.getWritableDatabase();
        Cursor cursor = database.rawQuery(selectQuery, null);
        if(cursor.moveToFirst()) {
            do{
                HashMap<String, String> map = new HashMap<String , String>();
                map.put("idkamar", cursor.getString(0));
                map.put("nokamar", cursor.getString(1));
                map.put("tipekamar", cursor.getString(2));
                map.put("lantai", cursor.getString(3));
                map.put("biaya", cursor.getString(4));
                map.put("status", cursor.getString(5));
                data.add(map);
            } while (cursor.moveToNext());
        }
        database.close();
        return data;
    }
    public ArrayList<HashMap<String, String>> showAllDataKamarTerisi(){
        ArrayList<HashMap<String, String>> data;
        data = new ArrayList<HashMap<String, String>>();
        String selectQuery = "SELECT * FROM kamar  WHERE  status = 'Terisi' AND idkamar != 1 ORDER BY idkamar DESC";
        SQLiteDatabase database = dbSqlite.getWritableDatabase();
        Cursor cursor = database.rawQuery(selectQuery, null);
        if(cursor.moveToFirst()) {
            do{
                HashMap<String, String> map = new HashMap<String , String>();
                map.put("idkamar", cursor.getString(0));
                map.put("nokamar", cursor.getString(1));
                map.put("tipekamar", cursor.getString(2));
                map.put("lantai", cursor.getString(3));
                map.put("biaya", cursor.getString(4));
                map.put("status", cursor.getString(5));
                data.add(map);
            } while (cursor.moveToNext());
        }
        database.close();
        return data;
    }

    public List<String> ambiltipekamar(){
        List<String> tipekamar = new ArrayList<String>();

        String selectQuery = "SELECT tipekamar FROM kamar WHERE status = 'Tersedia' OR idkamar = 1";

        SQLiteDatabase db = dbSqlite.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // Perulangan sejumlah data yang ada dan tambahkan ke list.
        if (cursor.moveToFirst()) {
            do {
                tipekamar.add(cursor.getString(0));
            } while (cursor.moveToNext());
        }
        // closing connection
        cursor.close();
        db.close();

        // returning lables
        return tipekamar;
    }


    public ArrayList<HashMap<String, String>> showDataKamarById(int id){
        ArrayList<HashMap<String, String>> data;
        data = new ArrayList<HashMap<String, String>>();
        String selectQuery = "SELECT * FROM kamar WHERE idkamar= '"+ id +"'";
        SQLiteDatabase database = dbSqlite.getWritableDatabase();
        Cursor cursor = database.rawQuery(selectQuery, null);
        if(cursor.moveToFirst()) {
            do{
                HashMap<String, String> map = new HashMap<String , String>();
                map.put("idkamar", cursor.getString(0));
                map.put("nokamar", cursor.getString(1));
                map.put("tipekamar", cursor.getString(2));
                map.put("lantai", cursor.getString(3));
                map.put("biaya", cursor.getString(4));
                map.put("status", cursor.getString(5));
                data.add(map);
            } while (cursor.moveToNext());
        }
        database.close();
        return data;
    }
    public ArrayList<HashMap<String, String>> search(String keyword) {
        ArrayList<HashMap<String, String>> data;
        data = new ArrayList<HashMap<String, String>>();
        String selectQuery = "SELECT * FROM kamar WHERE status = 'Tersedia' AND (nokamar LIKE  '%" +keyword+ "%')  OR (tipekamar LIKE '%" +keyword+ "%') AND status = 'Tersedia'  AND tipekamar != 'Pilih Tipe Kamar'";
        SQLiteDatabase database = dbSqlite.getReadableDatabase();
        Cursor cursor = database.rawQuery(selectQuery, null);
        if(cursor.moveToFirst()) {
            do{
                HashMap<String, String> map = new HashMap<String , String>();
                map.put("idkamar", cursor.getString(0));
                map.put("nokamar", cursor.getString(1));
                map.put("tipekamar", cursor.getString(2));
                map.put("lantai", cursor.getString(3));
                map.put("biaya", cursor.getString(4));
                map.put("status", cursor.getString(5));
                data.add(map);
            } while (cursor.moveToNext());
        }
        database.close();
        return data;
    }


    public ArrayList<HashMap<String, String>> searchTdk(String keyword) {
        ArrayList<HashMap<String, String>> data;
        data = new ArrayList<HashMap<String, String>>();
        String selectQuery = "SELECT * FROM kamar WHERE status = 'Tidak Tersedia' AND (nokamar LIKE  '%" +keyword+ "%')  OR (tipekamar LIKE '%" +keyword+ "%') AND status = 'Tidak Tersedia'  AND tipekamar != 'Pilih Tipe Kamar'";
        SQLiteDatabase database = dbSqlite.getReadableDatabase();
        Cursor cursor = database.rawQuery(selectQuery, null);
        if(cursor.moveToFirst()) {
            do{
                HashMap<String, String> map = new HashMap<String , String>();
                map.put("idkamar", cursor.getString(0));
                map.put("nokamar", cursor.getString(1));
                map.put("tipekamar", cursor.getString(2));
                map.put("lantai", cursor.getString(3));
                map.put("biaya", cursor.getString(4));
                map.put("status", cursor.getString(5));
                data.add(map);
            } while (cursor.moveToNext());
        }
        database.close();
        return data;
    }

    public ArrayList<HashMap<String, String>> searchSemuakmr(String keyword) {
        ArrayList<HashMap<String, String>> data;
        data = new ArrayList<HashMap<String, String>>();
        String selectQuery = "SELECT * FROM kamar WHERE (nokamar LIKE  '%" +keyword+ "%')  OR (tipekamar LIKE '%" +keyword+ "%') AND idkamar != 1 ";
        SQLiteDatabase database = dbSqlite.getReadableDatabase();
        Cursor cursor = database.rawQuery(selectQuery, null);
        if(cursor.moveToFirst()) {
            do{
                HashMap<String, String> map = new HashMap<String , String>();
                map.put("idkamar", cursor.getString(0));
                map.put("nokamar", cursor.getString(1));
                map.put("tipekamar", cursor.getString(2));
                map.put("lantai", cursor.getString(3));
                map.put("biaya", cursor.getString(4));
                map.put("status", cursor.getString(5));
                data.add(map);
            } while (cursor.moveToNext());
        }
        database.close();
        return data;
    }

    public ArrayList<HashMap<String, String>> searchTerisi(String keyword) {
        ArrayList<HashMap<String, String>> data;
        data = new ArrayList<HashMap<String, String>>();
        String selectQuery = "SELECT * FROM kamar WHERE status = 'Terisi'  AND idkamar != 1 AND (nokamar LIKE  '%" +keyword+ "%')  OR (tipekamar LIKE '%" +keyword+ "%') AND status = 'Terisi'  AND idkamar != 1";
        SQLiteDatabase database = dbSqlite.getReadableDatabase();
        Cursor cursor = database.rawQuery(selectQuery, null);
        if(cursor.moveToFirst()) {
            do{
                HashMap<String, String> map = new HashMap<String , String>();
                map.put("idkamar", cursor.getString(0));
                map.put("nokamar", cursor.getString(1));
                map.put("tipekamar", cursor.getString(2));
                map.put("lantai", cursor.getString(3));
                map.put("biaya", cursor.getString(4));
                map.put("status", cursor.getString(5));
                data.add(map);
            } while (cursor.moveToNext());
        }
        database.close();
        return data;
    }


    public ArrayList<HashMap<String, String>> searchReservasi(String keyword){
        ArrayList<HashMap<String, String>> data;
        data = new ArrayList<HashMap<String, String>>();
        String selectQuery = "SELECT idreservasi, namacust, nohp, tglcekin, tglcekout, kamar.tipekamar, reservasi.nokamar, reservasi.lantai, kamar.biaya, hari,total, reservasi.status  FROM reservasi INNER JOIN kamar ON reservasi.tipekamar = kamar.idkamar WHERE reservasi.status = 'Cekin' AND namacust LIKE '%" +keyword+ "%' OR nohp LIKE '%" +keyword+ "%' AND reservasi.status = 'Cekin' ";
        SQLiteDatabase database = dbSqlite.getWritableDatabase();
        Cursor cursor = database.rawQuery(selectQuery, null);
        if(cursor.moveToFirst()) {
            do{
                HashMap<String, String> map = new HashMap<String , String>();
                map.put("idreservasi", cursor.getString(0));
                map.put("namacust", cursor.getString(1));
                map.put("nohp", cursor.getString(2));
                map.put("tglcekin", cursor.getString(3));
                map.put("tglcekout", cursor.getString(4));
                map.put("kamar.tipekamar", cursor.getString(5));
                map.put("reservasi.nokamar", cursor.getString(6));
                map.put("reservasi.lantai", cursor.getString(7));
                map.put("kamar.biaya", cursor.getString(8));
                map.put("hari", cursor.getString(9));
                map.put("total", cursor.getString(10));
                map.put("reservasi.status", cursor.getString(11));
                data.add(map);
            } while (cursor.moveToNext());
        }
        database.close();
        return data;
    }

    public ArrayList<HashMap<String, String>> searchReservasiCekout(String keyword){
        ArrayList<HashMap<String, String>> data;
        data = new ArrayList<HashMap<String, String>>();
        String selectQuery = "SELECT idreservasi, namacust, nohp, tglcekin, tglcekout, kamar.tipekamar, reservasi.nokamar, reservasi.lantai, kamar.biaya, hari,total, reservasi.status  FROM reservasi INNER JOIN kamar ON reservasi.tipekamar = kamar.idkamar WHERE reservasi.status = 'Cekout' AND namacust LIKE '%" +keyword+ "%' OR nohp LIKE '%" +keyword+ "%' AND reservasi.status = 'Cekout' ";
        SQLiteDatabase database = dbSqlite.getWritableDatabase();
        Cursor cursor = database.rawQuery(selectQuery, null);
        if(cursor.moveToFirst()) {
            do{
                HashMap<String, String> map = new HashMap<String , String>();
                map.put("idreservasi", cursor.getString(0));
                map.put("namacust", cursor.getString(1));
                map.put("nohp", cursor.getString(2));
                map.put("tglcekin", cursor.getString(3));
                map.put("tglcekout", cursor.getString(4));
                map.put("kamar.tipekamar", cursor.getString(5));
                map.put("reservasi.nokamar", cursor.getString(6));
                map.put("reservasi.lantai", cursor.getString(7));
                map.put("kamar.biaya", cursor.getString(8));
                map.put("hari", cursor.getString(9));
                map.put("total", cursor.getString(10));
                map.put("reservasi.status", cursor.getString(11));
                data.add(map);
            } while (cursor.moveToNext());
        }
        database.close();
        return data;
    }
    public ArrayList<HashMap<String, String>> searchSemuaReservasi(String keyword){
        ArrayList<HashMap<String, String>> data;
        data = new ArrayList<HashMap<String, String>>();
        String selectQuery = "SELECT idreservasi, namacust, nohp, tglcekin, tglcekout, kamar.tipekamar, reservasi.nokamar, reservasi.lantai, kamar.biaya, hari,total, reservasi.status  FROM reservasi INNER JOIN kamar ON reservasi.tipekamar = kamar.idkamar WHERE namacust LIKE '%" +keyword+ "%' OR nohp LIKE '%" +keyword+ "%' ";
        SQLiteDatabase database = dbSqlite.getWritableDatabase();
        Cursor cursor = database.rawQuery(selectQuery, null);
        if(cursor.moveToFirst()) {
            do{
                HashMap<String, String> map = new HashMap<String , String>();
                map.put("idreservasi", cursor.getString(0));
                map.put("namacust", cursor.getString(1));
                map.put("nohp", cursor.getString(2));
                map.put("tglcekin", cursor.getString(3));
                map.put("tglcekout", cursor.getString(4));
                map.put("kamar.tipekamar", cursor.getString(5));
                map.put("reservasi.nokamar", cursor.getString(6));
                map.put("reservasi.lantai", cursor.getString(7));
                map.put("kamar.biaya", cursor.getString(8));
                map.put("hari", cursor.getString(9));
                map.put("total", cursor.getString(10));
                map.put("reservasi.status", cursor.getString(11));
                data.add(map);
            } while (cursor.moveToNext());
        }
        database.close();
        return data;
    }




    public  List<String> getNokamar(String inputan) {

        List<String> nokamar = new ArrayList<String>();

        String selectQuery = "SELECT nokamar FROM kamar WHERE tipekamar = '"+inputan+"' ";

        SQLiteDatabase db = dbSqlite.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // Perulangan sejumlah data yang ada dan tambahkan ke list.
        if (cursor.moveToFirst()) {
            do {
                nokamar.add(cursor.getString(0));
            } while (cursor.moveToNext());
        }
        // closing connection
        cursor.close();
        db.close();

        // returning lables
        return nokamar;
    }

    public  List<String> getLantai(String inputan) {

        List<String> lantai = new ArrayList<String>();

        String selectQuery = "SELECT lantai FROM kamar WHERE tipekamar = '"+inputan+"' ";

        SQLiteDatabase db = dbSqlite.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // Perulangan sejumlah data yang ada dan tambahkan ke list.
        if (cursor.moveToFirst()) {
            do {
                lantai.add(cursor.getString(0));
            } while (cursor.moveToNext());
        }
        // closing connection
        cursor.close();
        db.close();

        // returning lables
        return lantai;
    }
}
