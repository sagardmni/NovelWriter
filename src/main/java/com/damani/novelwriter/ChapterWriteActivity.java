package com.damani.novelwriter;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class ChapterWriteActivity extends AppCompatActivity {
    private int chapterId;
    private int novelId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chapter_write);
        Intent intent = getIntent();
        Bundle b = intent.getExtras();
        chapterId = b.getInt(ChapterListActivity.CHAPTER_KEY);
        novelId = b.getInt(NovelListActivity.NOVEL_KEY);
        //Fetch text from chapterId
        SQLiteOpenHelper novelDatabaseHelper = new NovelDatabaseHelper(getApplicationContext(), "NOVELDB", null, 1);
        SQLiteDatabase db = novelDatabaseHelper.getReadableDatabase();
        //Query database
        Cursor cursor = db.rawQuery("select CONTENT from CHAPTER where _id="+chapterId, null);
        cursor.moveToFirst();
        //Put the old text back into text box
        String myText = cursor.getString(cursor.getColumnIndex("CONTENT"));
        EditText editText = (EditText)findViewById(R.id.chapter_text);
        editText.setText(myText);
    }

    public void onClickCancelText(View view){
        Intent intent = new Intent(this,ChapterListActivity.class);
        Bundle b = new Bundle();
        b.putInt(NovelListActivity.NOVEL_KEY,novelId);
        intent.putExtras(b);
        startActivity(intent);
        finish();
    }

    public void onClickSaveText(View view){
        EditText editText = (EditText) findViewById(R.id.chapter_text);
        //Insert into db
        SQLiteOpenHelper novelDatabaseHelper = new NovelDatabaseHelper(getApplicationContext(), "NOVELDB", null, 1);
        SQLiteDatabase db = novelDatabaseHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("CONTENT",editText.getText().toString());
        db.update("CHAPTER",contentValues,"_id = ?", new String[] {Integer.toString(chapterId)});
        //Go back to Chapter List
        Intent intent = new Intent(this,ChapterListActivity.class);
        Bundle b = new Bundle();
        b.putInt(NovelListActivity.NOVEL_KEY,novelId);
        intent.putExtras(b);
        startActivity(intent);
        finish();
    }

    public void onClickDeleteChapter(View view){
        SQLiteOpenHelper novelDatabaseHelper = new NovelDatabaseHelper(getApplicationContext(), "NOVELDB", null, 1);
        SQLiteDatabase db = novelDatabaseHelper.getWritableDatabase();
        db.delete("CHAPTER","_id=?",new String[] {Integer.toString(chapterId)});
        Intent intent = new Intent(this,ChapterListActivity.class);
        Bundle b = new Bundle();
        b.putInt(NovelListActivity.NOVEL_KEY,novelId);
        intent.putExtras(b);
        startActivity(intent);
    }
}
