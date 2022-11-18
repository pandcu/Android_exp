package com.example.componentexp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        View listview = findViewById(R.id.listviewbtn);
        listview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ListviewActivity.class);
                startActivity(intent);
            }
        });

        View alertdialog = findViewById(R.id.AlertDialogbtn);
        alertdialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                View layout = View.inflate(MainActivity.this, R.layout.alertdialog, null);
                builder.setView(layout);
                AlertDialog dialog = builder.show();
                layout.findViewById(R.id.cancelbtn).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });
                layout.findViewById(R.id.signinbtn).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });

            }
        });

        View menu = findViewById(R.id.menubtn);
        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, MenuActivity.class);
                startActivity(intent);
            }
        });

        View actionmode = findViewById(R.id.actionbtn);
        actionmode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ActionmodeActivity.class);
                startActivity(intent);
            }
        });
    }
}