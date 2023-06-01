package com.example.todo;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

public class ActivitySchedule extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedual);

        SharedPreferences sharedPreferences = getSharedPreferences("item_data", Context.MODE_PRIVATE);

        // 初始化一个空表
        List<WeekViewItem> weekViewItemList = new ArrayList<>();

        // 初始化周历,共24行，一行代表一小时
        for (int i = 0; i < 24; ++i)
            weekViewItemList.add(new WeekViewItem(i));

        // 读取itemList
        List<Item> itemList = new ArrayList<>();
        for (int i = 0; i < sharedPreferences.getInt("Item number", 0); ++i)
            itemList.add(Item.parseJson(sharedPreferences.getString("item" + i, "")));

        WeekViewItem wItem;
//         遍历所有item，将每个item对应到周历的具体位置
        for (Item item : itemList) {
            wItem = weekViewItemList.get(item.deadline[3]);
            wItem.items[item.getDayOfWeek()] = item;
        }

        ListView listView = findViewById(R.id.week_view_list);
        WeekViewItemAdapter adapter = new WeekViewItemAdapter(ActivitySchedule.this, weekViewItemList);
        listView.setAdapter(adapter);

        // 底部导航栏
        BottomNavigationView bottomNavigationView = findViewById(R.id.week_view_navigation);
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
    }

}