package com.example.todo;

import java.util.Arrays;
import java.util.Calendar;

public class Item {
    /*
     * 待办条目
     * */
    public static String[] imp_str = {"不重要", "一般", "比较重要", "重要", "非常重要"};

    // 重要性对应的颜色不再有ItemAdapter计算，而是计算好之后存储起来，直接使用。
    public static int[] imp_clr = {-65535, -32768, -256, -8323328, -16711936};
    public String title;//标题
    public String content;//内容
    public int[] deadline;//截止日期
    public int importance;// 重要性

    public Item(String t, String c, int[] d, int i) {
        title = t;
        content = c;
        deadline = d;
        importance = i;
    }

    public Item() {
        title = "";
        content = "";
        deadline = new int[5];
        importance = 0;
    }

    public String str() {
        return title + " " + content + " " + Arrays.toString(deadline) + " " + importance;
    }

    public String toJsonString() {
        Json json = new Json();
        json.put("title", title);
        json.put("content", content);
        json.put("deadline_year", String.valueOf(deadline[0]));
        json.put("deadline_month", String.valueOf(deadline[1]));
        json.put("deadline_day", String.valueOf(deadline[2]));
        json.put("deadline_hour", String.valueOf(deadline[3]));
        json.put("deadline_minute", String.valueOf(deadline[4]));
        json.put("importance", String.valueOf(importance));
        return json.toString();
    }



    public static Item parseJson(String jsonStr) {
        Item item = new Item();
        Json json = Json.parse(jsonStr);
        item.title = json.get("title");
        item.content = json.get("content");
        item.deadline[0] = Integer.parseInt(json.get("deadline_year"));
        item.deadline[1] = Integer.parseInt(json.get("deadline_month"));
        item.deadline[2] = Integer.parseInt(json.get("deadline_day"));
        item.deadline[3] = Integer.parseInt(json.get("deadline_hour"));
        item.deadline[4] = Integer.parseInt(json.get("deadline_minute"));
        item.importance = Integer.parseInt(json.get("importance"));
        return item;
    }

    public int isEarly(int[] date) {
        Calendar cal1 = Calendar.getInstance();
        cal1.set(deadline[0], deadline[1], deadline[2], deadline[3], deadline[4]);
        Calendar cal2 = Calendar.getInstance();
        cal2.set(date[0], date[1], date[2], date[3], date[4]);
        return (int) (cal2.getTimeInMillis() - cal1.getTimeInMillis());
    }

    public int getDayOfWeek() {
        Calendar cal = Calendar.getInstance();
        cal.set(deadline[0], deadline[1], deadline[2], deadline[3], deadline[4]);
        return cal.get(Calendar.DAY_OF_WEEK);
    }

    public int[] CountTime() {
        Calendar now = Calendar.getInstance();

        Calendar cal = Calendar.getInstance();
        cal.set(deadline[0], deadline[1], deadline[2], deadline[3], deadline[4]);

        long millis = cal.getTimeInMillis() - now.getTimeInMillis();
        long hours = millis / (1000 * 60 * 60);
        long days = hours / 24;
        int[] rlt = new int[2];
        rlt[0] = (int) days;
        rlt[1] = (int) hours % 24;
        return rlt;
    }

    public String CountTimeStr() {
        Calendar now = Calendar.getInstance();

        Calendar cal = Calendar.getInstance();
        cal.set(deadline[0], deadline[1], deadline[2], deadline[3], deadline[4]);

        int millis = (int) (cal.getTimeInMillis() - now.getTimeInMillis());
        int hours = millis / (1000 * 60 * 60);
        int days = hours / 24;
        hours = hours % 24;
        return days + "天" + hours + "小时";
    }

    public String getDeadLineStr() {
        return deadline[0] + "年" + deadline[1] + "月" + deadline[2] + "日" + deadline[3] + "时" + deadline[4] + "分";
    }
}
