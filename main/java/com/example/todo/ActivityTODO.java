package com.example.todo;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
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
import java.util.Calendar;
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
    private ItemAdapter adapter;
    // 是否降序
    private boolean isDescending = false;
    // 底部导航栏
    private BottomNavigationView bottomNavigationView;

    // 输入按钮
    private EditText titleEditText; // 输入标题
    private EditText contentEditText; // 输入内容
    private Button addDate;
    private Button addTime;
    private SeekBar seekBar;// 重要性选择滑动条
    private Button addButton;// 添加按钮

    // 提醒功能
    private AlarmManager alarmManager;
    private PendingIntent alarmIntent;

    // 文件读写
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todo);

        initSharedPreferences();

        // 初始化数据
        initData();

        // 初始化控件
        initView();

        // 初始化列表
        initListView();
    }

    void initSharedPreferences() {
        sharedPreferences = getSharedPreferences("item_data", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }


    private void initData() {
        itemList = readListFromFile();
//        itemList.add(new Item("aaa", "aaaaa", new int[]{1, 2, 3, 4, 5}, 0));
//        itemList.add(new Item("bbb", "bbbbb", new int[]{12, 4, 12, 12, 52}, 3));
//        itemList.add(new Item("ccc", "ccccc", new int[]{18, 8, 28, 18, 58}, 4));
        System.out.println("Read File" + itemList.size());
        Calendar calendar = Calendar.getInstance();

        // 为每一个条目设置闹钟
        for (Item item : itemList) {
            // 获取item的deadline对应的时间戳
            calendar.set(Calendar.YEAR, item.deadline[0]);
            calendar.set(Calendar.MONTH, item.deadline[1]);
            calendar.set(Calendar.DAY_OF_MONTH, item.deadline[2]);
            calendar.set(Calendar.HOUR_OF_DAY, item.deadline[3]);
            calendar.set(Calendar.MINUTE, item.deadline[4]);
            calendar.set(Calendar.SECOND, 0);
            calendar.set(Calendar.MILLISECOND, 0);
            long triggerTime = calendar.getTimeInMillis();

//            // TODO 设置闹钟
//            alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
//            Intent intent = new Intent(this, AlarmReceiver.class);
//
//            // 传递字符串
//            intent.putExtra("title", item.title);
//            intent.putExtra("content", item.content);
//
//            alarmIntent = PendingIntent.getBroadcast(this, 0, intent, 0);
//
//            alarmManager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, triggerTime, alarmIntent);
        }

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

        // 单个条目的点击事件
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                showItemDialog(position);
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
                        closeAlarm();
                        return true;
                    case R.id.menu_schedule:
                        saveListToFile();
                        closeAlarm();
                        intent = new Intent(ActivityTODO.this, ActivitySchedule.class);
                        startActivity(intent);
                        return true;
                    case R.id.menu_setup:
                        saveListToFile();
                        closeAlarm();
                        intent = new Intent(ActivityTODO.this, ActivitySetUp.class);
                        startActivity(intent);
                        return true;
                }
                return false;
            }
        });
        bottomNavigationView.setSelectedItemId(R.id.menu_todo); // 底部菜单栏默认选择待办页面

        // 添加按钮的点击事件
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showCustomDialog();
            }
        });
    }


    // 创建一个提示框，用来显示条目的详情。
    @SuppressLint("SetTextI18n")
    private void showItemDialog(final int position) {
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.item_dialog);

        TextView title = dialog.findViewById(R.id.item_title);
        TextView content = dialog.findViewById(R.id.item_content);
        TextView deadline = dialog.findViewById(R.id.item_deadline);
        TextView importance = dialog.findViewById(R.id.item_importance);

        title.setText(itemList.get(position).title);
        content.setText(itemList.get(position).content);

        deadline.setText(itemList.get(position).getDeadLineStr());
        importance.setText(Item.imp_str[itemList.get(position).importance]);

        Button deleteButton = dialog.findViewById(R.id.delete_button);
        Button returnButton = dialog.findViewById(R.id.return_button);

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                itemList.remove(position);
                adapter.notifyDataSetChanged();
                dialog.dismiss();
            }
        });

        returnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    // 创建一个提示框，用来输入待办信息
    @SuppressLint("SetTextI18n")
    private void showCustomDialog() {
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.add_dialog);
        dialog.setTitle("添加待办");

        // 获取提示框控件
        titleEditText = dialog.findViewById(R.id.title_edit_text);
        contentEditText = dialog.findViewById(R.id.content_edit_text);
        addDate = dialog.findViewById(R.id.add_date);
        addTime = dialog.findViewById(R.id.add_time);
        seekBar = dialog.findViewById(R.id.seek_bar);

        // 用于显示当前选择的日期和时间
        TextView dateView = dialog.findViewById(R.id.date_text);
        TextView timeView = dialog.findViewById(R.id.time_text);

        // 选择日期和时间
        final Calendar c = Calendar.getInstance();
        final int[] tYear = {c.get(Calendar.YEAR)};
        final int[] tMonth = {c.get(Calendar.MONTH)};
        final int[] tDay = {c.get(Calendar.DAY_OF_MONTH)};

        dateView.setText(tYear[0] + ":" + tMonth[0] + ":" + tDay[0]);

        addDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DatePickerDialog datePickDialog = new DatePickerDialog(ActivityTODO.this, new DatePickerDialog.OnDateSetListener() {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        // 选择日期之后的操作
                        tYear[0] = year;
                        tMonth[0] = month;
                        tDay[0] = dayOfMonth;

                        dateView.setText(tYear[0] + ":" + tMonth[0] + ":" + tDay[0]);
                    }
                }, tYear[0], tMonth[0], tDay[0]);
                datePickDialog.show();
            }
        });


        final int[] tHour = {c.get(Calendar.HOUR_OF_DAY)};
        final int[] tMinute = {c.get(Calendar.MINUTE)};

        timeView.setText(tHour[0] + ":" + tMinute[0]);

        addTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerDialog timePickerDialog = new TimePickerDialog(ActivityTODO.this,
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                                tHour[0] = hourOfDay;
                                tMinute[0] = minute;
                                timeView.setText(tHour[0] + ":" + tMinute[0]);
                            }
                        }, tHour[0], tMinute[0], true);
                timePickerDialog.show();
            }
        });

        int[] deadline = new int[5];
        deadline[0] = tYear[0];
        deadline[1] = tMonth[0];
        deadline[2] = tDay[0];
        deadline[3] = tHour[0];
        deadline[4] = tMinute[0];

        // 设置seekbar的最大值和初始值
        seekBar.setMax(4);
        seekBar.setProgress(0);


        TextView addImportance = dialog.findViewById(R.id.add_importance);
        addImportance.setText("重要性: " + Item.imp_str[0]);

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                addImportance.setText("重要性：" + Item.imp_str[progress]);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

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
                int progress = seekBar.getProgress();

                // TODO 处理输入的value
                itemList.add(new Item(title, content, deadline, progress));
                sortData(sortSpinner.getSelectedItemPosition());
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    private void closeAlarm() {
//        alarmManager.cancel(alarmIntent);
    }


    private void initListView() {
        adapter = new ItemAdapter(ActivityTODO.this, itemList);
        listView.setAdapter(adapter);
    }

    private void sortData(int position) {
        switch (position) {
            case 0:// 按照deadline排序
                itemList.sort(new Comparator<Item>() {
                    @Override
                    public int compare(Item item, Item t1) {
                        return isDescending ? item.isEarly(t1.deadline) : t1.isEarly(item.deadline);
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


    public void saveListToFile() {
        int itemNum = itemList.size();
        editor.putInt("Item number", itemNum);
        for (int i = 0; i < itemNum; ++i) {
            editor.putString("item" + i, itemList.get(i).toJsonString());
        }
        editor.apply();
    }

    public List<Item> readListFromFile() {
        List<Item> itemList = new ArrayList<>();

        int itemNum = sharedPreferences.getInt("Item number", 0);
        for (int i = 0; i < itemNum; ++i) {
            String s = sharedPreferences.getString("item" + i, "");
            itemList.add(Item.parseJson(s));
        }

        return itemList;
    }

}