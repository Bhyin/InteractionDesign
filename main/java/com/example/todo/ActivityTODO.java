package com.example.todo;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.ToggleButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class ActivityTODO extends AppCompatActivity {
    // 下拉选项框
    private Spinner sortSpinner;
    // 升序/降序切换
    private ToggleButton toggleButton;
    private ListView listView;
    // 条目列表
    private List<Item> itemList;
    // 列表适配器
    private MyAdapter adapter;
    // 是否降序
    private boolean isDescending = false;
    // 底部导航栏
    private BottomNavigationView bottomNavigationView;

    // 输入按钮
    private EditText titleEditText; // 输入标题
    private EditText contentEditText; // 输入内容
    private TimePicker timePicker;// 日期时间选项卡
    private SeekBar seekBar;// 重要性选择滑动条
    private Button addButton;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todo);

        // 初始化数据
        initData();

        // 初始化控件
        initView();

        // 初始化列表
        initListView();
    }

    private void initData() {
        itemList = new ArrayList<>();
    }

    private void initView() {
        sortSpinner = findViewById(R.id.sort_spinner);
        toggleButton = findViewById(R.id.sort_toggle);
        listView = findViewById(R.id.item_list);
        bottomNavigationView = findViewById(R.id.todo_navigation);
        addButton = findViewById(R.id.add_button);


        // 设置下拉选项框的选项
        ArrayAdapter<CharSequence> arrayAdapter = ArrayAdapter.createFromResource(
                ActivityTODO.this,
                R.array.sort_options,
                androidx.constraintlayout.widget.R.layout.support_simple_spinner_dropdown_item);
        arrayAdapter.setDropDownViewResource(androidx.constraintlayout.widget.R.layout.support_simple_spinner_dropdown_item);
        sortSpinner.setAdapter(arrayAdapter);

        // 下拉选项框的选择事件
        sortSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                sortData(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        // 切换按钮的点击事件
        toggleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isDescending = !isDescending;
                sortData(sortSpinner.getSelectedItemPosition());
            }
        });

        // 底部菜单栏的点击事件
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @SuppressLint("NonConstantResourceId")
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Intent intent;
                switch (item.getItemId()) {
                    case R.id.menu_todo:
                        return true;
                    case R.id.menu_schedule:
                        intent = new Intent(ActivityTODO.this, ActivitySchedule.class);
                        startActivity(intent);
                        return true;
                    case R.id.menu_setup:
                        intent = new Intent(ActivityTODO.this, ActivitySetUp.class);
                        startActivity(intent);
                        return true;
                }
                return false;
            }
        });
        bottomNavigationView.setSelectedItemId(R.id.menu_todo);

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showCustomDialog();
            }
        });
    }

    // 创建一个提示框，用来输入待办信息
    private void showCustomDialog() {
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.add_dialog);
        dialog.setTitle("添加待办");

        // 获取提示框控件
        titleEditText = dialog.findViewById(R.id.title_edit_text);
        contentEditText = dialog.findViewById(R.id.content_edit_text);
        timePicker = dialog.findViewById(R.id.time_picker);
        seekBar = dialog.findViewById(R.id.seek_bar);

        // 设置seekbar的最大值和初始值
        seekBar.setMax(4);
        seekBar.setProgress(0);

        // 对话框的确认和返回按钮
        Button cancelButton = dialog.findViewById(R.id.cancel_button);
        Button okButton = dialog.findViewById(R.id.ok_button);

        // 取消按钮的点击事件
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        // 确认按钮的点击事件
        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 获取输入的值
                String title = titleEditText.getText().toString();
                String content = contentEditText.getText().toString();
                if (title.isEmpty()) {
                    // 标题为空，内容也为空
                    if (content.isEmpty()) dialog.dismiss();
                    else { // 标题为空，但内容不为空，就把标题的部分内容赋值给标题
                        if (content.length() > 3)
                            title = content.substring(0, 2);
                        else title = content;
                    }
                }
                int hour = timePicker.getCurrentHour();
                int minute = timePicker.getCurrentMinute();
                int progress = seekBar.getProgress();

                // TODO 处理输入的value
                itemList.add(new Item(title, content, hour + ":" + minute, progress));
                sortData(sortSpinner.getSelectedItemPosition());
                dialog.dismiss();
            }
        });
        dialog.show();
    }


    private void initListView() {
        adapter = new MyAdapter(ActivityTODO.this, itemList);
        listView.setAdapter(adapter);
    }

    private void sortData(int position) {
        switch (position) {
            case 0:// 按照deadline排序
                itemList.sort(new Comparator<Item>() {
                    @Override
                    public int compare(Item item, Item t1) {
                        return isDescending ? item.deadline.compareTo(t1.deadline) : t1.deadline.compareTo(item.deadline);
                    }
                });
                break;
            case 1:// 按照重要性排序
                itemList.sort(new Comparator<Item>() {
                    @Override
                    public int compare(Item item, Item t1) {
                        return isDescending ? item.importance - t1.importance : t1.importance - item.importance;
                    }
                });
                break;
        }
        adapter.notifyDataSetChanged();
    }

    private static class MyAdapter extends ArrayAdapter<Item> {

        public MyAdapter(@NonNull ActivityTODO context, List<Item> itemList) {
            super(context, 0, itemList);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.item, parent, false);
            }

            TextView leftText = convertView.findViewById(R.id.left_text);
            TextView rightText = convertView.findViewById(R.id.right_text);

            Item item = getItem(position);

            leftText.setText(item.title);
            rightText.setText(item.deadline);
            return convertView;
        }
    }
}