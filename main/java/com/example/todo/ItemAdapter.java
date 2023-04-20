package com.example.todo;

import android.content.Context;
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

        TextView title = convertView.findViewById(R.id.left_text);
        TextView deadline = convertView.findViewById(R.id.right_text);

        title.setText(itemList.get(position).title);
        deadline.setText(itemList.get(position).deadline);
        return convertView;
    }
}
