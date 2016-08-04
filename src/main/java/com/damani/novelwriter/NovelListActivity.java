package com.damani.novelwriter;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
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
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class NovelListActivity extends AppCompatActivity {
    public static final String NOVEL_KEY="novel key";
    private ArrayList<Integer> novelList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_novel_list);
        ListView listView = (ListView) findViewById(R.id.novel_list);
        //Create database
        try {
            SQLiteOpenHelper novelDatabaseHelper = new NovelDatabaseHelper(getApplicationContext(), "NOVELDB", null, 1);
            SQLiteDatabase db = novelDatabaseHelper.getReadableDatabase();
            //Query database
            Cursor cursor = db.rawQuery("select * from NOVEL", null);
            //push _id into ArrayList
            if(cursor!=null && cursor.getCount() > 0) {
                cursor.moveToFirst();
                do {
                    novelList.add(cursor.getInt(cursor.getColumnIndex("_id")));
                } while (cursor.moveToNext());
                //populate list of novels
                cursor.moveToFirst();
                CursorAdapter listAdapter = new SimpleCursorAdapter(this, android.R.layout.simple_list_item_1,
                        cursor, new String[]{"NAME"},
                        new int[]{android.R.id.text1}, 0);
                listView.setAdapter(listAdapter);
            }
        } catch (SQLiteException e) {
            Toast toast = Toast.makeText(this,"Database unavailable",Toast.LENGTH_SHORT);
            toast.show();
        }
        //Create listener for click on novel
        AdapterView.OnItemClickListener novelClickListener = new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                //Use 'id' to get the actual novel id from ArrayList
                //position vs id?
                int actualId = novelList.get(position);
                Intent intent = new Intent(NovelListActivity.this,ChapterListActivity.class);
                Bundle b = new Bundle();
                b.putInt(NOVEL_KEY,actualId);
                intent.putExtras(b);
                startActivity(intent);
            }
        };
        listView.setOnItemClickListener(novelClickListener);
    }

    public void onClickNewNovel(View view){
        Intent intent = new Intent(this,AddNovelActivity.class);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
