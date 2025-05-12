package com.example.lab1.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.NonNull;

import com.example.lab1.api.FilmsAPI;
import com.example.lab1.api.JsonParser;
import com.example.lab1.model.Film;
import com.example.lab1.model.User;

import org.json.JSONException;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

// https://www.youtube.com/watch?v=sq45s9gggsw

public class DBHelper extends SQLiteOpenHelper {
    public static final int DB_VERSION = 1;
    public static final String DB_NAME = "projectDb.db";
    public static String DB_PATH = "/data/user/0/com.example.lab1/databases/";

    SQLiteDatabase db;
    private final Context mContext;

    public DBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        this.mContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {}
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {}

    private boolean doesDatabaseExist() {
        try {
            final String mPath = DB_PATH + DB_NAME;
            final File file = new File(mPath);
            return file.exists();
        } catch (SQLiteException e) {
            e.printStackTrace();
            return false;
        }
    }

    private void copyDatabase() throws IOException { // Copies the db from the file in assets to memory
        try {
            InputStream mInputStream = mContext.getAssets().open(DB_NAME);
            String outFileName = DB_PATH + DB_NAME;
            OutputStream mOutputStream = new FileOutputStream(outFileName);
            byte[] buffer = new byte[1024];
            int length;
            while ( (length = mInputStream.read(buffer)) > 0 ) {
                mOutputStream.write(buffer, 0, length);
            }
            mOutputStream.flush();
            mOutputStream.close();
            mInputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void createDatabaseFromFile() throws IOException {
        boolean mDatabaseExists = doesDatabaseExist();

        if (!mDatabaseExists) {
            this.getReadableDatabase(); //prevent writing to a non-existing db
            this.close();
        }

        try {
            this.copyDatabase();
        } catch (IOException e) {
            e.printStackTrace();
            throw new Error("Error copying database");
        } finally {
            this.close();
        }
    }

    @Override
    public synchronized void close() {
        if (db != null) { db.close(); }
        SQLiteDatabase.releaseMemory();
        super.close();
    }

    public ArrayList<User> getUsers() {
        try {
            createDatabaseFromFile();
        } catch (IOException e) {
            e.printStackTrace();
        }

        ArrayList<User> users = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM users", null);

        while (cursor.moveToNext()) {
            String resultLogin = cursor.getString(1);
            String resultPass = cursor.getString(2);
            users.add(new User(resultLogin, resultPass));
        }

        cursor.close();
        sqLiteDatabase.close();
        return users;
    }

    private void createFilmsTable() {
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        sqLiteDatabase.execSQL("CREATE TABLE films(id INTEGER NOT NULL PRIMARY KEY, nameRu TEXT, nameOrig TEXT, genres TEXT, year INTEGER, poster TEXT, countries TEXT, description TEXT)");
    }

    private void insertFilm(SQLiteDatabase sqLiteDatabase, Film film) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("id", film.id);
        contentValues.put("nameRu", film.nameRu);
        contentValues.put("nameOrig", film.nameOrig);
        contentValues.put("genres", film.genres);
        contentValues.put("year", film.year);
        contentValues.put("poster", film.poster);
        contentValues.put("countries", film.countries);
        contentValues.put("description", film.description);

        sqLiteDatabase.insertOrThrow("films", null, contentValues);
    }

    private void fetchFilms(Callback fetchCallback) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();

        FilmsAPI.Requests.getTopFilms(mContext, new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                fetchCallback.onFailure(call, e);
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                try {
                    ArrayList<Film> filmsParsed = JsonParser.parseLongInfo(response.body().string());

                    sqLiteDatabase.beginTransaction(); // For batch insert
                    try {
                        for (Film elem : filmsParsed) {
                            insertFilm(sqLiteDatabase, elem);
                        }
                        sqLiteDatabase.setTransactionSuccessful();
                        // Response is unused here
                        // Use this callback to get the data from the db and pass it to the ViewModel
                        fetchCallback.onResponse(call, response);

                    } catch (Exception e) {
                        e.printStackTrace();

                    } finally {
                        sqLiteDatabase.endTransaction();
                    }

                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }

    public ArrayList<Film> getShortInfo() {
        ArrayList<Film> tmpFilms = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM films", null);

        while (cursor.moveToNext()) {
            Film tmpFilm = new Film();
            tmpFilm.nameRu = cursor.getString(1);
            tmpFilm.nameOrig = cursor.getString(2);
            tmpFilm.genres = cursor.getString(3);
            tmpFilm.year = cursor.getInt(4);
            tmpFilms.add(tmpFilm);
        }

        cursor.close();
        return tmpFilms;
    }

    public ArrayList<Film> getLongInfo() {
        ArrayList<Film> tmpFilms = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM films", null);

        while (cursor.moveToNext()) {
            Film tmpFilm = new Film();
            tmpFilm.nameRu = cursor.getString(1);
            tmpFilm.nameOrig = cursor.getString(2);
            tmpFilm.genres = cursor.getString(3);
            tmpFilm.year = cursor.getInt(4);
            tmpFilm.poster = cursor.getString(5);
            tmpFilm.countries = cursor.getString(6);
            tmpFilm.description = cursor.getString(7);
            tmpFilms.add(tmpFilm);
        }

        cursor.close();
        return tmpFilms;
    }

    public void loadFilmsIntoDb(Callback fetchCallback) {
        try {
            createFilmsTable();
            fetchFilms(fetchCallback);
        } catch (SQLiteException e) {
            e.printStackTrace();
            Log.i("SQLiteLog", "(intended behaviour)");
        }
    }
}
