package com.example.todo;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class ActivitySetUp extends AppCompatActivity {
    private BottomNavigationView bottomNavigationView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_up);
        initView();

    }


    private void initView() {
        bottomNavigationView = findViewById(R.id.setup_navigation);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @SuppressLint("NonConstantResourceId")
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Intent intent;
                switch (item.getItemId()) {
                    case R.id.menu_todo:

                        intent = new Intent(ActivitySetUp.this, ActivityTODO.class);
                        startActivity(intent);
                        return true;
                    case R.id.menu_schedule:
                        intent = new Intent(ActivitySetUp.this, ActivitySchedule.class);
                        startActivity(intent);
                        return true;
                    case R.id.menu_setup:
                        return true;
                }
                return false;
            }
        });
    }

}