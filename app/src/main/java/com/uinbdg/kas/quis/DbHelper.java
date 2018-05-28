package com.uinbdg.kas.quis;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.util.ArrayList;
import java.util.List;

public class DbHelper extends SQLiteOpenHelper {
    private static final String ID = "id";
    private static final String JAWAB = "jawaban";
    private static final String KEY_OPTIONA = "a";
    private static final String KEY_OPTIONB = "b";
    private static final String KEY_OPTIONC = "c";
    private static final String NAMA_DATABASES = "sundaQuis";
    private static final String TABEL_PERTANYAAN = "tanya";
    private static final String TANYA = "pertanyaan";
    private static final int VERSI_DATABASES = 1;
    private SQLiteDatabase dbase;

    public DbHelper(Context context) {
        super(context, NAMA_DATABASES, null, 1);
    }

    public void onCreate(SQLiteDatabase db) {
        this.dbase = db;
        db.execSQL("CREATE TABLE IF NOT EXISTS tanya ( id INTEGER PRIMARY KEY AUTOINCREMENT, pertanyaan TEXT, jawaban TEXT, a TEXT, b TEXT, c TEXT)");
        addQuestions();
    }

    private void addQuestions() {
        addQuestion(new Pertanyaan("1. Pada prasasti apakah aksara sunda pertama kali ditemukan?", " Kawali", " Yupa", " Batu", " Kawali"));
        addQuestion(new Pertanyaan("2. Terjemahkan tanggal dibawah ini kedalam bentuk latin?", " 29 Oktober 2013", " 30 Oktober 2013", " 31 Oktober 2013", " 31 Oktober 2013"));
        addQuestion(new Pertanyaan("3. Terjemahan kata dibawah adalah?", " Android", " April", " Aksara", " Android"));
        addQuestion(new Pertanyaan("4. Alamat dibawah ini jika ditulis dengan latin menjadi?", " Jl. Sunan Bonang No. 21 Tuban", " Jl. Sunan Bonang No. 10 Tuban", " Jl. Sunan Bonang No. 17 Tuban", " Jl. Sunan Bonang No. 10 Tuban"));
        addQuestion(new Pertanyaan("5. Aksara dibawah ini berbunyi?", " a", "e", " eu", " eu"));
        addQuestion(new Pertanyaan("6. Mengubah, menambah, dan meghilangkan bunyi vokal aksara dasar adalah fungsi dari aksara?", " Rarangken", " Dasar", " Swara", " Rarangken"));
        addQuestion(new Pertanyaan("7. Aksara dibawah ini termasuk dalam kategori?", " Swara", " Konsonan", " Ngalagena", " Konsonan"));
        addQuestion(new Pertanyaan("8. Bentuk Aksara Sunda berpangkal dari?", " Pallawa", " Hanacaraka", " Carakan", " Pallawa"));
        addQuestion(new Pertanyaan("9. Rarangken dibawah ini akan mengubah bunyi aksara dasar \"ka\" menjadi?", " kah", " ku", " kir", " kah"));
        addQuestion(new Pertanyaan("10. Berapa banyak Aksara Swara?", " 32", " 10", " 7", " 7"));
    }

    public void onUpgrade(SQLiteDatabase db, int oldV, int newV) {
        db.execSQL("DROP TABLE IF EXISTS tanya");
        onCreate(db);
    }

    public void addQuestion(Pertanyaan quest) {
        ContentValues values = new ContentValues();
        values.put(TANYA, quest.getPERTANYAAN());
        values.put(JAWAB, quest.getJAWAB());
        values.put(KEY_OPTIONA, quest.getA());
        values.put(KEY_OPTIONB, quest.getB());
        values.put(KEY_OPTIONC, quest.getC());
        this.dbase.insert(TABEL_PERTANYAAN, null, values);
    }

    public List<Pertanyaan> getAllQuestions() {
        List<Pertanyaan> daftarTanya = new ArrayList();
        this.dbase = getReadableDatabase();
        Cursor cursor = this.dbase.rawQuery("SELECT  * FROM tanya", null);
        if (cursor.moveToFirst()) {
            do {
                Pertanyaan quest = new Pertanyaan();
                quest.setID(cursor.getInt(0));
                quest.setPERTANYAAN(cursor.getString(1));
                quest.setJAWAB(cursor.getString(2));
                quest.setA(cursor.getString(3));
                quest.setB(cursor.getString(4));
                quest.setC(cursor.getString(5));
                daftarTanya.add(quest);
            } while (cursor.moveToNext());
        }
        return daftarTanya;
    }

    public int rowcount() {
        return getWritableDatabase().rawQuery("SELECT  * FROM tanya", null).getCount();
    }
}
