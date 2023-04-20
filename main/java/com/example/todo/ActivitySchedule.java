package com.example.todo;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TableLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

public class ActivitySchedule extends AppCompatActivity {
    private TableLayout calendarTableLayout;
    private TextView[][] calendarCells = new TextView[24][7];
    private BottomNavigationView bottomNavigationView;

    private List<Item> itemList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule);


        initData();
        initView();
    }

    private void initData() {
        itemList = readListFromFile();
        System.out.println("The length of item list is " + itemList.size());
    }

    private void initView() {
        bottomNavigationView = findViewById(R.id.schedule_navigation);
        calendarTableLayout = findViewById(R.id.calendar_table_layout);

        // 底部导航栏点击事件
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @SuppressLint("NonConstantResourceId")
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Intent intent;
                switch (item.getItemId()) {
                    case R.id.menu_todo:
                        intent = new Intent(ActivitySchedule.this, ActivityTODO.class);
                        startActivity(intent);
                        return true;
                    case R.id.menu_schedule:
                        return true;
                    case R.id.menu_setup:
                        intent = new Intent(ActivitySchedule.this, ActivitySetUp.class);
                        startActivity(intent);
                        return true;
                }
                return false;
            }
        });

        // 初始化周历视图的各个格子
        for (int i = 0; i < 24; ++i) {
            for (int j = 0; j < 7; ++j) {
                @SuppressLint("DiscouragedApi") int cellId = getResources().getIdentifier("w" + j + "_h" + i, "id", getPackageName());
                calendarCells[i][j] = findViewById(cellId);
            }
        }

        for (Item item : itemList) {
            int w = 2;
            String[] parts = item.deadline.split(":");
            int h = Integer.parseInt(parts[0]);
            calendarCells[h][w].setText(item.title);
        }
        // TODO 设置周历滑动事件

    }

    public List<Item> readListFromFile() {
        SharedPreferences sharedPreferences = getSharedPreferences("item_data", Context.MODE_PRIVATE);

        List<Item> itemList = new ArrayList<>();
        String[] weekdays = {"0", "1", "2", "3", "4", "5", "6"};
        String[] times = {"01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13",
                "14", "15", "16", "17", "18", "19", "20", "21", "22", "23"};

        for (String w : weekdays) {
            for (String t : times) {
                String title = sharedPreferences.getString(w + "_" + t + "_title", "");
                if (title.equals("")) continue;
                String content = sharedPreferences.getString(w + "_" + t + "_content", "");
                String deadline = sharedPreferences.getString(w + "_" + t + "_deadline", "01:00");
                int importance = sharedPreferences.getInt(w + "_" + t + "_importance", -1);

//                System.out.println("The weekday is " + w + ",\n and the time is " + t);
//
//                System.out.println("The content is really " + sharedPreferences.getString(w + "_" + t + "_content", "01:00"));
//                System.out.println("The deadline is really " + sharedPreferences.getString(w + "_" + t + "_deadline", "01:00"));
//                System.out.println("++++++++++++++++++++++++++++++++++++++++++++++");

                itemList.add(new Item(title, content, deadline, importance));
            }

        }
        return itemList;

    }
}