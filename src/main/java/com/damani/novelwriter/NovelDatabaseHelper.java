package com.damani.novelwriter;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by sagard on 2/8/16.
 */
public class NovelDatabaseHelper extends SQLiteOpenHelper {

    public NovelDatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE NOVEL ( _id INTEGER PRIMARY KEY AUTOINCREMENT, "
                                +" NAME TEXT);");
        sqLiteDatabase.execSQL("CREATE TABLE CHAPTER ( _id INTEGER PRIMARY KEY AUTOINCREMENT, "
                                +" TITLE TEXT, CONTENT TEXT, NOVEL_ID INTEGER, FOREIGN KEY(NOVEL_ID) REFERENCES NOVEL(_id));");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
