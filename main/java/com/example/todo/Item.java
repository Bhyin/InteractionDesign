package com.example.todo;

public class Item {
    /*
     * 待办条目
     * */
    public String title;//标题
    public String content;//内容
    public String deadline;//截止日期
    public int importance;// 重要性

    public Item(String t, String c, String d, int i) {
        title = t;
        content = c;
        deadline = d;
        importance = i;
    }
}
