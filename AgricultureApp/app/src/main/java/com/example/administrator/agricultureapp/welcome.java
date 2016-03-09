package com.example.administrator.agricultureapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
public class welcome extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        //final Window window = getWindow();// ��ȡ��ǰ�Ĵ������
        //window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);// ������״̬��
        //requestWindowFeature(Window.FEATURE_NO_TITLE);// �����˱�����
        setContentView(R.layout.activity_welcome);
        welcomeUI();
    }
    private void welcomeUI()
    {
        new Thread(new Runnable()
        {

            @Override
            public void run()
            {
                try
                {
                    Thread.sleep(3000);
                    Message message = new Message();
                    welHandler.sendMessage(message);// ������Ϣ�а���ʲô����������Ҫ����Ϊ���յĺ�������Ҫ�ò���
                } catch (InterruptedException e)
                {
                    e.printStackTrace();
                }
            }
        }).start();
    }
    Handler welHandler = new Handler()
    {

        @Override
        public void handleMessage(Message msg)
        {
            welcomeFunction();
        }

    };
    public void welcomeFunction()
    {
        Intent intent = new Intent();
        intent.setClass(welcome.this,classify.class);
        startActivity(intent);
        welcome.this.finish();
    }

}