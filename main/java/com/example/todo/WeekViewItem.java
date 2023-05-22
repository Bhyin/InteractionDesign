package com.example.todo;

public class WeekViewItem {
    public int time;
    public Item[] items = new Item[7];

    public WeekViewItem(int t) {
        time = t;
        for (int i = 0; i < 7; ++i) {
            items[i] = null;
        }
    }
}
