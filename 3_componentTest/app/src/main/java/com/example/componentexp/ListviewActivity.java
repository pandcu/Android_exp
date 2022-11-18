package com.example.componentexp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ListviewActivity extends AppCompatActivity {
    private String[] animalname = {"Lion", "Tiger", "Monkey", "Dog", "Cat", "Elephant"};
    private int[] animalimage = {R.drawable.lion, R.drawable.tiger, R.drawable.monkey, R.drawable.dog, R.drawable.cat, R.drawable.elephant};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listview);
        List<Map<String, Object>> listItems = new ArrayList<>();
        for(int i = 0; i < animalname.length; i++){
            Map<String, Object> listItem = new HashMap<String, Object>();
            listItem.put("text", animalname[i]);
            listItem.put("image", animalimage[i]);
            listItems.add(listItem);
        }
        SimpleAdapter simpleAdapter = new SimpleAdapter(this, listItems, R.layout.listview_item, new String[]{"text", "image"}, new int[]{R.id.animalName, R.id.animalImage});
        ListView list = findViewById(R.id.animal);
        list.setAdapter(simpleAdapter);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                TextView textView = view.findViewById(R.id.animalName);
                Toast toast = Toast.makeText(getApplicationContext(), textView.getText(), Toast.LENGTH_SHORT);
                toast.show();
            }
        });
    }
}