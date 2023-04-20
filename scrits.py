# 快速生成布局文件的xml代码
for i in range(5, 25):
    time = str(i).rjust(2, '0')
    print(
        f"""
<TableRow
    android:layout_weight="1"
    android:gravity="center_horizontal">
    <TextView
        android:layout_width="0dp"
        android:layout_height="wrap_content"
        android:layout_weight="1"
        android:text="@string/h{time}"/>

    <TextView
        android:id="@+id/w0_h{time}"
        android:layout_width="0dp"
        android:layout_height="wrap_content"
        android:layout_weight="1"
        android:text=""/>

    <TextView
        android:id="@+id/w1_h{time}"
        android:layout_width="0dp"
        android:layout_height="wrap_content"
        android:layout_weight="1"
        android:text=""/>

    <TextView
        android:id="@+id/w2_h{time}"
        android:layout_width="0dp"
        android:layout_height="wrap_content"
        android:layout_weight="1"
        android:text=""/>

    <TextView
        android:id="@+id/w3_h{time}"
        android:layout_width="0dp"
        android:layout_height="wrap_content"
        android:layout_weight="1"
        android:text=""/>

    <TextView
        android:id="@+id/w4_h{time}"
        android:layout_width="0dp"
        android:layout_height="wrap_content"
        android:layout_weight="1"
        android:text=""/>

    <TextView
        android:id="@+id/w5_h{time}"
        android:layout_width="0dp"
        android:layout_height="wrap_content"
        android:layout_weight="1"
        android:text=""/>

    <TextView
        android:id="@+id/w6_h{time}"
        android:layout_width="0dp"
        android:layout_height="wrap_content"
        android:layout_weight="1"
        android:text=""/>

</TableRow>
""")
