package com.damani.novelwriter;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class ChapterListActivity extends AppCompatActivity {
    public static final String CHAPTER_KEY = "Chapter Key";
    private ArrayList<Integer> chapterList = new ArrayList<>();
    private int novelId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chapter_list);
        Intent intent = getIntent();
        Bundle myBundle = intent.getExtras();
        novelId = myBundle.getInt(NovelListActivity.NOVEL_KEY,0);
        SQLiteOpenHelper novelDatabaseHelper = new NovelDatabaseHelper(getApplicationContext(), "NOVELDB", null, 1);
        SQLiteDatabase db = novelDatabaseHelper.getReadableDatabase();
        //Query database
        Cursor cursor = db.rawQuery("select * from CHAPTER where NOVEL_ID="+novelId, null);
        //push _id of chapters into Arraylist
        if(cursor!=null && cursor.getCount() > 0) {
            cursor.moveToFirst();
            do {
                chapterList.add(cursor.getInt(cursor.getColumnIndex("_id")));
            } while (cursor.moveToNext());
            //populate
            ListView listView = (ListView) findViewById(R.id.chapter_list);
            CursorAdapter listAdapter = new SimpleCursorAdapter(this, android.R.layout.simple_list_item_1,
                    cursor, new String[]{"TITLE"},
                    new int[]{android.R.id.text1}, 0);
            listView.setAdapter(listAdapter);
            //Add listener for chapter-click
            AdapterView.OnItemClickListener chapterClickListener = new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                    int chapterId = chapterList.get(position);
                    Intent intent = new Intent(ChapterListActivity.this, ChapterWriteActivity.class);
                    Bundle b = new Bundle();
                    b.putInt(CHAPTER_KEY, chapterId);
                    b.putInt(NovelListActivity.NOVEL_KEY, novelId);
                    intent.putExtras(b);
                    startActivity(intent);
                }
            };
            listView.setOnItemClickListener(chapterClickListener);
        }
    }

    public void onClickNewChapter(View view){
        Intent intent = new Intent(this, AddChapterActivity.class);
        Bundle b = new Bundle();
        b.putInt(NovelListActivity.NOVEL_KEY,novelId);
        intent.putExtras(b);
        startActivity(intent);
    }

    public void onClickDone(View view){
        Intent intent = new Intent(this,NovelListActivity.class);
        startActivity(intent);
    }

    public void onClickDeleteNovel(View view){
        SQLiteOpenHelper novelDatabaseHelper = new NovelDatabaseHelper(getApplicationContext(), "NOVELDB", null, 1);
        SQLiteDatabase db = novelDatabaseHelper.getWritableDatabase();
        db.delete("NOVEL","_id=?",new String[] {Integer.toString(novelId)});
        Intent intent = new Intent(this,NovelListActivity.class);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this,NovelListActivity.class);
        startActivity(intent);
    }
}
