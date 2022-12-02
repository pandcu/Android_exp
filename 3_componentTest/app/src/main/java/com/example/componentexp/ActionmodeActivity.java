package com.example.componentexp;


import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ActionmodeActivity extends AppCompatActivity {

    private String[] number = {"One", "Two", "Three", "Four", "Five", "Six", "Seven", "Eight", "Night","Ten"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actionmode);
        List<Map<String, String>> listitems = new ArrayList<>();
        for (int i = 0; i < number.length; i++){
            Map<String, String> listitem = new HashMap<>();
            listitem.put("number", number[i]);
            listitems.add(listitem);
        }
        SimpleAdapter adapter = new SimpleAdapter(ActionmodeActivity.this, listitems, R.layout.actionnodelist_item, new String[]{"number"}, new int[]{R.id.numbertext});
        ListView list = findViewById(R.id.actionmodelist);
        list.setAdapter(adapter);
        list.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE_MODAL);
        list.setMultiChoiceModeListener(new AbsListView.MultiChoiceModeListener() {
            int count = 0;
            @Override
            public void onItemCheckedStateChanged(ActionMode actionMode, int i, long l, boolean b) {
                count = list.getCheckedItemCount();
                actionMode.setTitle(count + " select");
            }

            @Override
            public boolean onCreateActionMode(ActionMode actionMode, Menu menu) {
                MenuInflater inflater = actionMode.getMenuInflater();
                inflater.inflate(R.menu.actionmode_menu, menu);
                return true;

            }

            @Override
            public boolean onPrepareActionMode(ActionMode actionMode, Menu menu) {
                return false;
            }

            @Override
            public boolean onActionItemClicked(ActionMode actionMode, MenuItem menuItem) {
                return false;
            }

            @Override
            public void onDestroyActionMode(ActionMode actionMode) {

            }
        });

    }

}