package com.example.todo;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class AlarmReceiver extends BroadcastReceiver {
    @SuppressLint("SetTextI18n")
    @Override
    public void onReceive(Context context, Intent intent) {
        // 显示弹窗
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        @SuppressLint("InflateParams") View view = LayoutInflater.from(context).inflate(R.layout.alarm_dialog, null);
        TextView title = view.findViewById(R.id.alarm_title);
        TextView content = view.findViewById(R.id.alarm_content);
        Button confirmButton = view.findViewById(R.id.alarm_confirm_button);
        Button cancelButton = view.findViewById(R.id.alarm_cancel_button);

        String title_str=intent.getStringExtra("title");
        String content_str=intent.getStringExtra("content");

        // 在弹窗中显示标题和内容
        title.setText(title_str + "的时间已经到了！");
        content.setText(content_str);

        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, "点击确认按钮", Toast.LENGTH_SHORT).show();
                closeDialog(context);
            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                closeDialog(context);
            }
        });

        builder.setView(view);
        builder.setCancelable(false);
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void closeDialog(Context context) {
        // 关闭弹窗
        Intent closeIntent = new Intent(Intent.ACTION_CLOSE_SYSTEM_DIALOGS);
        context.sendBroadcast(closeIntent);
    }


}
