package com.example.todo;

public class Item {
    public String title;
    public String content;
    public String deadline;
    public int importance;

    Item(String t, String c, String d, int i) {
        title = t;
        content = c;
        deadline = d;
        importance = i;
    }
}
