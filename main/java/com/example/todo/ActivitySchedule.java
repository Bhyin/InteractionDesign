package com.example.todo;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class ActivitySchedule extends AppCompatActivity {
    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule);

        initView();
    }

    private void initView(){
        bottomNavigationView=findViewById(R.id.schedule_navigation);

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