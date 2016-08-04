package com.damani.novelwriter;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class AddNovelActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_novel);
    }

    public void onClickCancel(View view){
        Intent intent = new Intent(this,NovelListActivity.class);
        startActivity(intent);
        finish();
    }

    public void onClickSave(View view){
        EditText editText = (EditText) findViewById(R.id.enter_novel_title);
        //Insert into db
        SQLiteOpenHelper novelDatabaseHelper = new NovelDatabaseHelper(getApplicationContext(), "NOVELDB", null, 1);
        SQLiteDatabase db = novelDatabaseHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("NAME",editText.getText().toString());
        db.insert("NOVEL",null,contentValues);
        //Back to Novel List
        Intent intent = new Intent(this,NovelListActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this,NovelListActivity.class);
        startActivity(intent);
        finish();
    }
}
