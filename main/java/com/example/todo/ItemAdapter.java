package com.example.todo;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
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

    private int startColor;
    private int endColor;


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

        // 最低的importance是绿色，最高的是红色
        startColor = Color.GREEN;
        endColor = Color.RED;
        // 计算出其它importance对应的颜色
        int[] colors = new int[5];

        float[] hsvStartColor = new float[3];
        float[] hsvEndColor = new float[3];

        // 转化为hsv格式
        Color.colorToHSV(startColor, hsvStartColor);
        Color.colorToHSV(endColor, hsvEndColor);

        // 计算渐变颜色，有5个层次对应五个importance
        float[] hsv = new float[3];
        // 计算变化量
        float deltaH = (hsvStartColor[0] - hsvEndColor[0]) / 4;
        float deltaS = (hsvStartColor[1] - hsvEndColor[1]) / 4;
        float deltaV = (hsvStartColor[2] - hsvEndColor[2]) / 4;
        // 插值
        for (int i = 0; i < 5; i++) {
            hsv[0] = hsvStartColor[0] + i * deltaH;
            hsv[1] = hsvStartColor[1] + i * deltaS;
            hsv[2] = hsvStartColor[2] + i * deltaV;
            colors[i] = Color.HSVToColor(hsv);
        }

        // 设置渐变的颜色
        int[] gradientColor = {colors[itemList.get(position).importance], Color.WHITE};
        GradientDrawable gradientDrawable = new GradientDrawable(GradientDrawable.Orientation.LEFT_RIGHT, gradientColor);
        convertView.setBackground(gradientDrawable);

        TextView title = convertView.findViewById(R.id.left_text);
        TextView deadline = convertView.findViewById(R.id.right_text);

        title.setText(itemList.get(position).title);
        deadline.setText(itemList.get(position).deadline);
        return convertView;
    }
}
