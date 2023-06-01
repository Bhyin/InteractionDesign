package com.example.todo;

import android.widget.ArrayAdapter;

import java.util.Comparator;
import java.util.List;

public class SortItemList {
    public static boolean isDescending = false;
    public static ArrayAdapter<Item> adapter;

    public static int position = 0;

    public static void sort(List<Item> itemList) {
        switch (position) {
            case 0:// 按照deadline排序
                itemList.sort(new Comparator<Item>() {
                    @Override
                    public int compare(Item item, Item t1) {
                        int res;

                        if (item.isFinished && !t1.isFinished) res = 1;
                        else if (!item.isFinished && t1.isFinished) res = -1;
                        else
                            res = isDescending ? item.getCountdown(t1.deadline) : t1.getCountdown(item.deadline);
                        return res;
                    }
                });
                break;
            case 1:// 按照重要性排序
                itemList.sort(new Comparator<Item>() {
                    @Override
                    public int compare(Item item, Item t1) {
                        int res;
                        if (item.isFinished && !t1.isFinished) res = 1;
                        else if (!item.isFinished && t1.isFinished) res = -1;
                        else
                            res = isDescending ? item.importance - t1.importance : t1.importance - item.importance;
                        return res;
                    }
                });
                break;
        }
        adapter.notifyDataSetChanged();
    }
}
