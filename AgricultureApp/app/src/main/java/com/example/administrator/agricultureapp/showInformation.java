package com.example.administrator.agricultureapp;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class showInformation extends Activity {
    TextView textView;
    String imageFileUri;
    private List<Map<String,Object>> list=new ArrayList<Map<String,Object>>();
    private String title[]={"name","area","part","characteristic","reason","method","more"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_information);
        Bundle extras=this.getIntent().getExtras();
        imageFileUri = extras.getString("MyPicture");
        String msg=extras.getString("data");
        final String []mess=msg.split("\\n");
        for(int i=0;i<title.length;i++) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("title", title[i]);
            map.put("info", mess[i]);
            list.add(map);
        }

        SimpleAdapter adapter = new SimpleAdapter(this,list,R.layout.item,
                new String[]{"title","info"},
                new int[]{R.id.title,R.id.info});
        ((ListView)findViewById(R.id.ListView)).setAdapter(adapter);
//        setListAdapter(adapter);
 //       textView=(TextView)findViewById(R.id.textView);
 //       textView.setText(msg);
        findViewById(R.id.back2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(showInformation.this, MainActivity.class);
                startActivity(intent);
                showInformation.this.finish();
            }
        });
        findViewById(R.id.wenhao).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent();
                intent.setClass(showInformation.this, correct.class);
                Bundle bundle=new Bundle();
                bundle.putString("MyPicture",imageFileUri);
                bundle.putString("Aname",mess[0]);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_show_information, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        if (keyCode == KeyEvent.KEYCODE_BACK )
        {
            // 创建退出对话框
            AlertDialog isExit = new AlertDialog.Builder(this).create();
            // 设置对话框标题
            isExit.setTitle("系统提示");
            // 设置对话框消息
            isExit.setMessage("Are you sure to submit?");
            // 添加选择按钮并注册监听
            isExit.setButton("确定", listener);
            isExit.setButton2("取消", listener);
            // 显示对话框
            isExit.show();

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
                    showInformation.this.finish();
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
