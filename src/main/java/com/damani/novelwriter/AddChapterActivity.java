package com.damani.novelwriter;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class AddChapterActivity extends AppCompatActivity {
    private int novelId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_chapter);
        Intent intent = getIntent();
        Bundle b = intent.getExtras();
        novelId = b.getInt(NovelListActivity.NOVEL_KEY);
    }

    public void onClickCancelChapter(View view){
        Intent intent = new Intent(this,ChapterListActivity.class);
        Bundle b = new Bundle();
        b.putInt(NovelListActivity.NOVEL_KEY,novelId);
        intent.putExtras(b);
        startActivity(intent);
        finish();
    }

    public void onClickSaveChapter(View view){
        EditText editText = (EditText) findViewById(R.id.enter_chapter_title);
        //Insert into db
        SQLiteOpenHelper novelDatabaseHelper = new NovelDatabaseHelper(getApplicationContext(), "NOVELDB", null, 1);
        SQLiteDatabase db = novelDatabaseHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("TITLE",editText.getText().toString());
        contentValues.put("NOVEL_ID",Integer.toString(novelId));
        db.insert("CHAPTER",null,contentValues);

        Intent intent = new Intent(this,ChapterListActivity.class);
        Bundle b = new Bundle();
        b.putInt(NovelListActivity.NOVEL_KEY,novelId);
        intent.putExtras(b);
        startActivity(intent);
        finish();
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this,ChapterListActivity.class);
        startActivity(intent);
        finish();
    }
}
