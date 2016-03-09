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
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.SimpleAdapter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class classify extends Activity {

    private GridView gridView;
    private List<Map<String,Object>> list;
    private SimpleAdapter sim_adapter;
    private String []name={"corn","wheat","pea","rice","sunflower","potato","peanut","broomcorn",""};
    private int[] imgId={R.drawable.yumi,R.drawable.xiaomai,R.drawable.wandou,R.drawable.shuidao,
            R.drawable.xiangrikui,R.drawable.tudou,R.drawable.huasheng,R.drawable.gaoliang,R.drawable.jiahao};
    //--------------------------------设置GridView信息----------------------------------------
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_classify);
        gridView=(GridView)findViewById(R.id.gridview);
        list=new ArrayList<Map<String,Object>>();
        for(int i=0;i<name.length;i++)
        {
            Map<String,Object> map=new HashMap<String,Object>();
            map.put("image",imgId[i]);
            map.put("text",name[i]);
            list.add(map);
        }
        sim_adapter=new SimpleAdapter(this,list,R.layout.crop,new String[]{"image","text"},new int[]{R.id.image,R.id.text});
        gridView.setAdapter(sim_adapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent intent=new Intent();
                intent.setClass(classify.this,MainActivity.class);
                startActivity(intent);
                classify.this.finish();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_classify, menu);
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
    //-----------------------------系统返回键退出程序---------------------------------------
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        if (keyCode == KeyEvent.KEYCODE_BACK )
        {
            // 创建退出对话框
            AlertDialog isExit = new AlertDialog.Builder(this).create();
            // 设置对话框标题
            isExit.setTitle("System prompt");
            // 设置对话框消息
            isExit.setMessage("Are you sure to submit?");
            // 添加选择按钮并注册监听
            isExit.setButton("Yes", listener);
            isExit.setButton2("No", listener);
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
                    classify.this.finish();
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
