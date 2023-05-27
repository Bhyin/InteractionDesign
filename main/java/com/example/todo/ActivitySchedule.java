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
    private List<Item> itemList;
    private List<WeekViewItem> weekViewItemList;
    private ListView listView;
    // 底部导航栏
    private BottomNavigationView bottomNavigationView;

    // 文件读写
    SharedPreferences sharedPreferences;
    int itemNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedual);

        sharedPreferences = getSharedPreferences("item_data", Context.MODE_PRIVATE);

        initData();

        initView();
    }

    private void initData() {
        // 读取文件
        itemList = readListFromFile();

        // 初始化一个空表
        weekViewItemList = new ArrayList<>();

        // 初始化周历,共24行，一行代表一小时
        for (int i = 0; i < 24; ++i) {
            weekViewItemList.add(new WeekViewItem(i));
        }

        int w, h;
        WeekViewItem wItem;
//         遍历所有item，将每个item对应到周历的具体位置

        for (Item item : itemList) {
            w = item.getDayOfWeek();
            h = item.deadline[3];
            wItem = weekViewItemList.get(h);
            wItem.items[w] = item;
        }
    }

    private void initView() {
        bottomNavigationView = findViewById(R.id.test_week_view_navigation);
        listView = findViewById(R.id.week_view_list);
        WeekViewItemAdapter adapter = new WeekViewItemAdapter(ActivitySchedule.this, weekViewItemList);
        listView.setAdapter(adapter);

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
    }

    public List<Item> readListFromFile() {
        List<Item> itemList = new ArrayList<>();

        itemNumber = sharedPreferences.getInt("Item number", 0);
        for (int i = 0; i < itemNumber; ++i) {
            String s = sharedPreferences.getString("item" + i, "");
            itemList.add(Item.parseJson(s));
            System.out.println("Schedule: " + s);
        }
        return itemList;
    }
}