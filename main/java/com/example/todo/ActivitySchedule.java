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
            int w = item.getDayOfWeek();
            int h = item.deadline[3];
            calendarCells[h][w].setText(item.title);
        }
        // TODO 设置周历滑动事件

    }

    public List<Item> readListFromFile() {
        SharedPreferences sharedPreferences = getSharedPreferences("item_data", Context.MODE_PRIVATE);

        List<Item> itemList = new ArrayList<>();
        String[] weekdays = {"0", "1", "2", "3", "4", "5", "6"};
        String[] times = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13",
                "14", "15", "16", "17", "18", "19", "20", "21", "22", "23"};

        for (String w : weekdays) {
            for (String t : times) {
                String title = sharedPreferences.getString(w + "_" + t + "_title", "");
                if (title.equals("")) continue;
                String content = sharedPreferences.getString(w + "_" + t + "_content", "");

                int[] deadline = new int[5];
                deadline[0] = Integer.parseInt(sharedPreferences.getString(w + '_' + t + "_deadline_year", "0"));
                deadline[1] = Integer.parseInt(sharedPreferences.getString(w + '_' + t + "_deadline_month", "0"));
                deadline[2] = Integer.parseInt(sharedPreferences.getString(w + '_' + t + "_deadline_day", "0"));
                deadline[3] = Integer.parseInt(sharedPreferences.getString(w + '_' + t + "_deadline_hour", "0"));
                deadline[4] = Integer.parseInt(sharedPreferences.getString(w + '_' + t + "_deadline_minute", "0"));

                int importance = sharedPreferences.getInt(w + "_" + t + "_importance", -1);

                itemList.add(new Item(title, content, deadline, importance));
            }

        }
        return itemList;

    }
}