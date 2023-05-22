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

public class TestNewSchedule extends AppCompatActivity {
    private List<Item> itemList;
    private List<WeekViewItem> weekViewItemList;
    private ListView listView;
    private WeekViewItemAdapter adapter;

    // 底部导航栏
    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_new_schedual);

        initData();

        initView();

        initListView();
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

        int w,h;
        WeekViewItem wItem;
        // 遍历所有item，将每个item对应到周历的具体位置
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

        // 底部导航栏点击事件
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @SuppressLint("NonConstantResourceId")
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Intent intent;
                switch (item.getItemId()) {
                    case R.id.menu_todo:
                        intent = new Intent(TestNewSchedule.this, ActivityTODO.class);
                        startActivity(intent);
                        return true;
                    case R.id.menu_schedule:
                        return true;
                    case R.id.menu_setup:
                        intent = new Intent(TestNewSchedule.this, ActivitySetUp.class);
                        startActivity(intent);
                        return true;
                }
                return false;
            }
        });
    }

    private void initListView() {
        adapter = new WeekViewItemAdapter(TestNewSchedule.this, weekViewItemList);
        listView.setAdapter(adapter);
    }

    public void saveListToFile() {

        SharedPreferences sharedPreferences = getSharedPreferences("item_data", Context.MODE_PRIVATE);
        @SuppressLint("CommitPrefEdits") SharedPreferences.Editor editor = sharedPreferences.edit();

        for (Item item : itemList) {

            int w = item.getDayOfWeek();

            int t = item.deadline[3];

            editor.putString(w + "_" + t + "_title", item.title);
            editor.putString(w + "_" + t + "_content", item.content);
            editor.putString(w + "_" + t + "_deadline_year", String.valueOf(item.deadline[0]));
            editor.putString(w + "_" + t + "_deadline_month", String.valueOf(item.deadline[1]));
            editor.putString(w + "_" + t + "_deadline_day", String.valueOf(item.deadline[2]));
            editor.putString(w + "_" + t + "_deadline_hour", String.valueOf(item.deadline[3]));
            editor.putString(w + "_" + t + "_deadline_minute", String.valueOf(item.deadline[4]));
            editor.putInt(w + "_" + t + "_importance", item.importance);

        }
        editor.apply();
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