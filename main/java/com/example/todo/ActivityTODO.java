package com.example.todo;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;

public class ActivityTODO extends AppCompatActivity {

    String[] items = {"A", "B", "C", "ER", "DGS", "DFS", "DFE"};

    // 固定写法，进入activity时首先调用此方法
    @SuppressLint("ResourceAsColor")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 设置布局文件（必须）
        setContentView(R.layout.activity_todo);

        // 指定一个上下文，一个布局，一个内容列表
        ArrayAdapter<String> adapter = new ArrayAdapter<>(ActivityTODO.this, R.layout.item, new ArrayList<String>(Arrays.asList(items)));
        ListView listview = findViewById(R.id.item_list);
        listview.setAdapter(adapter);


        EditText editText = findViewById(R.id.edit);

        adapter.add("joaidhg");
        adapter.add("ppjdfo");
        adapter.add("inyhihoiu");

        Button add_button = findViewById(R.id.add_button);
        Button schedule_button = findViewById(R.id.todo_schedule_button);
        Button set_button = findViewById(R.id.todo_set_button);

        add_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String add = editText.getText().toString();
                adapter.add(add);
            }
        });

        schedule_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(ActivityTODO.this,ActivitySchedule.class);
                startActivity(intent);
            }
        });

        set_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(ActivityTODO.this,ActivitySetUp.class);
                startActivity(intent);
            }
        });

    }
}