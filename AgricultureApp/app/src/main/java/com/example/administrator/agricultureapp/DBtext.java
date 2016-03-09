package com.example.administrator.agricultureapp;


import android.app.Activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;

import android.os.Bundle;

import android.view.KeyEvent;
import android.widget.TabHost;
import android.widget.TabWidget;
import android.widget.TextView;



public class DBtext extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dbtext);
        TabHost tabHost=(TabHost)findViewById(android.R.id.tabhost);
        tabHost.setup();
        tabHost.addTab(tabHost.newTabSpec("tab1").setIndicator("disease").setContent(R.id.tv1));

        tabHost.addTab(tabHost.newTabSpec("tab2").setIndicator("pet").setContent(R.id.tv2));

        tabHost.addTab(tabHost.newTabSpec("tab2").setIndicator("others").setContent(R.id.tv2));
        tabHost.setCurrentTabByTag("tab1");

        TabWidget tabWidget=tabHost.getTabWidget();
        for (int i = 0; i < tabWidget.getChildCount(); i++) {
            TextView tv=(TextView)tabWidget.getChildAt(i).findViewById(android.R.id.title);
            tv.setGravity(BIND_AUTO_CREATE);
            tv.setPadding(10, 10,10, 10);
            tv.setTextSize(16);//设置字体的大小；
            tv.setTextColor(Color.parseColor("#e79145"));//设置字体的颜色；
        }
        }
    //------------------------------系统返回键退出程序-------------------------------------
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        if (keyCode == KeyEvent.KEYCODE_BACK )
        {
            Intent intent = new Intent();
            intent.setClass(DBtext.this, MainActivity.class);
            startActivity(intent);
            DBtext.this.finish();
        }

        return false;

    }
    /**监听对话框里面的button点击事件*/
    DialogInterface.OnClickListener listener = new DialogInterface.OnClickListener()
    {
        public void onClick(DialogInterface dialog, int which)
        {
            switch (which)
            {
                case AlertDialog.BUTTON_POSITIVE:// "确认"按钮退出程序
                    Intent intent = new Intent(Intent.ACTION_MAIN);
                    intent.addCategory(Intent.CATEGORY_HOME);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    DBtext.this.finish();
                    android.os.Process.killProcess(android.os.Process.myPid());
                    break;
                case AlertDialog.BUTTON_NEGATIVE:// "取消"第二个按钮取消对话框
                    break;
                default:
                    break;
            }
        }
    };
    }