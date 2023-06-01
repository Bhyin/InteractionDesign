package com.example.todo;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class ItemAdapter extends ArrayAdapter<Item> {
    /*
     * 待办列表适配器
     * */
    private final Context context;
    private final List<Item> itemList;


    public ItemAdapter(@NonNull Context context, List<Item> itemList) {
        super(context, 0, itemList);
        this.context = context;
        this.itemList = itemList;

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item, parent, false);
        }

        Item item = itemList.get(position);

        // 设置渐变的颜色
        final int[] gradientColor;
        if (item.isFinished) {
            gradientColor = new int[]{Color.GRAY, Color.WHITE};
        } else {
            gradientColor = new int[]{Item.imp_clr[item.importance], Color.WHITE};
        }
        final GradientDrawable gradientDrawable = new GradientDrawable(GradientDrawable.Orientation.LEFT_RIGHT, gradientColor);
        convertView.setBackground(gradientDrawable);

        // 视图左边的选项框，勾选后会修改排序的逻辑
        CheckBox checkBox = convertView.findViewById(R.id.checkbox);
        View finalConvertView = convertView;
        View finalConvertView1 = convertView;
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked)// 勾选
                {
                    final int[] gradientColor = {Color.GRAY, Color.WHITE};
                    final GradientDrawable gradientDrawable = new GradientDrawable(GradientDrawable.Orientation.LEFT_RIGHT, gradientColor);
                    finalConvertView.setBackground(gradientDrawable);
                    item.isFinished = true;
                    SortItemList.sort(itemList);
                } else {
                    // 设置渐变的颜色
                    final int[] gradientColor = {Item.imp_clr[itemList.get(position).importance], Color.WHITE};
                    final GradientDrawable gradientDrawable = new GradientDrawable(GradientDrawable.Orientation.LEFT_RIGHT, gradientColor);
                    finalConvertView1.setBackground(gradientDrawable);
                    item.isFinished = false;
                    SortItemList.sort(itemList);
                }
            }
        });

        // 设置条目的内容
        TextView title = convertView.findViewById(R.id.left_text);
        TextView deadline = convertView.findViewById(R.id.right_text);

        title.setText(item.title);
        deadline.setText(item.CountTimeStr());
        return convertView;
    }
}
