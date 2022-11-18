package com.example.componentexp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

public class MenuActivity extends AppCompatActivity {
    TextView txt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        txt = findViewById(R.id.testtxt);
        registerForContextMenu(txt);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.manu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.isCheckable()){
            item.setChecked(true);
        }
        switch (item.getItemId()){
            case R.id.big: txt.setTextSize(10); break;
            case R.id.middle: txt.setTextSize(16); break;
            case R.id.small: txt.setTextSize(20); break;
            case R.id.red: txt.setTextColor(Color.RED); break;
            case R.id.black: txt.setTextColor(Color.BLACK); break;
            case R.id.plain_item:
                Toast.makeText(MenuActivity.this, "点击了普通菜单项", Toast.LENGTH_SHORT).show();
                break;
        }
        return true;
    }
}