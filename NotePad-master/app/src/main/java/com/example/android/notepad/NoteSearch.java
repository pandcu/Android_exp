package com.example.android.notepad;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

public class NoteSearch extends Activity implements SearchView.OnQueryTextListener {

    ListView listview;
    SQLiteDatabase sqLiteDatabase;

    private static final String[] PROJECTION = new String[]{
            NotePad.Notes._ID,
            NotePad.Notes.COLUMN_NAME_TITLE,
            NotePad.Notes.COLUMN_NAME_MODIFICATION_DATE,
            NotePad.Notes.COLUMN_NAME_BACK_COLOR
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.note_search);
        SearchView searchView = findViewById(R.id.search_view);
        Intent intent = getIntent();
        if (intent.getData() == null){
            intent.setData(NotePad.Notes.CONTENT_URI);
        }
        listview = findViewById(R.id.search_list);
        sqLiteDatabase = new NotePadProvider.DatabaseHelper(this).getReadableDatabase();
        searchView.setSubmitButtonEnabled(true);
        searchView.setQueryHint("search");
        searchView.setOnQueryTextListener(this);
    }

    @Override
    public boolean onQueryTextSubmit(String s) {
        Toast.makeText(this, "you choose" + s, Toast.LENGTH_SHORT).show();
        return false;
    }

    @Override
    public boolean onQueryTextChange(String s) {
        String selection1 = NotePad.Notes.COLUMN_NAME_TITLE + " like ? or " + NotePad.Notes.COLUMN_NAME_NOTE + " like ? ";
        String[] selection2 = {"%"+s+"%", "%"+s+"%"};
        Cursor cursor = sqLiteDatabase.query(
                NotePad.Notes.TABLE_NAME,
                PROJECTION,
                selection1,
                selection2,
                null,
                null,
                NotePad.Notes.DEFAULT_SORT_ORDER
        );

        String[] dataColums = {
                NotePad.Notes.COLUMN_NAME_TITLE,
                NotePad.Notes.COLUMN_NAME_MODIFICATION_DATE
        };

        int[] viewIDs = {
                R.id.text1,
                R.id.text2
        };



        MyCursorAdapter adapter  = new MyCursorAdapter(this, R.layout.noteslist_item, cursor, dataColums, viewIDs);
        listview.setAdapter(adapter);

        return true;
    }
}
