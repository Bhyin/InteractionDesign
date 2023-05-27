package com.example.todo;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class WeekViewItemAdapter extends ArrayAdapter<WeekViewItem> {
    private final Context context;
    private final List<WeekViewItem> weekViewItemList;

    public WeekViewItemAdapter(@NonNull Context context, List<WeekViewItem> weekViewItemList) {
        super(context, 0, weekViewItemList);
        this.context = context;
        this.weekViewItemList = weekViewItemList;
    }

    @SuppressLint("SetTextI18n")
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.week_item, parent, false);
        }

        TextView hour;
        TextView[] w = new TextView[7];
        hour = convertView.findViewById(R.id.week_item_hour);
        w[0] = convertView.findViewById(R.id.week_item_0);
        w[1] = convertView.findViewById(R.id.week_item_1);
        w[2] = convertView.findViewById(R.id.week_item_2);
        w[3] = convertView.findViewById(R.id.week_item_3);
        w[4] = convertView.findViewById(R.id.week_item_4);
        w[5] = convertView.findViewById(R.id.week_item_5);
        w[6] = convertView.findViewById(R.id.week_item_6);

        resetTextView(w);

        WeekViewItem weekViewItem = weekViewItemList.get(position);
        System.out.println("Position: " + position);

        hour.setText(weekViewItem.time + "");

        for (int i = 0; i < 7; ++i) {
            Item item = weekViewItem.items[i];
            if (item != null) {
                // 设置textview的内容
                w[i].setText(item.title + position + i);

                // 按照紧急程度设置textview的颜色
                w[i].setBackgroundColor(Item.imp_clr[item.importance]);

                // 设置textview的点击事件
                w[i].setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // 弹窗显示item的具体信息
                        showItemDialog(item);
                    }
                });
            } else w[i].setText(""); // 什么都不显示
        }

        return convertView;
    }

    @SuppressLint("ResourceAsColor")
    void resetTextView(TextView[] textViews) {
        for (TextView textView : textViews) {
            textView.setBackgroundColor(Color.WHITE);
            textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
        }
    }

    // 创建一个提示框，用来显示条目的详情。
    @SuppressLint("SetTextI18n")
    private void showItemDialog(Item item) {
        final Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.week_view_item_dialog);

        TextView title = dialog.findViewById(R.id.week_view_item_title);
        TextView content = dialog.findViewById(R.id.week_view_item_content);
        TextView deadline = dialog.findViewById(R.id.week_view_item_deadline);
        TextView importance = dialog.findViewById(R.id.week_view_item_importance);

        title.setText(item.title);
        content.setText(item.content);

        deadline.setText(item.getDeadLineStr());
        importance.setText(Item.imp_str[item.importance]);

        Button returnButton = dialog.findViewById(R.id.week_view_return_button);

        returnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }

}
