package com.example.administrator.agricultureapp;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.StrictMode;
import android.provider.ContactsContract;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.UnknownHostException;


public class affirm extends Activity {
    Bitmap bitmap=null;
    ImageView imageView;
    ProgressDialog mDialog;
    Socket socket=null;
    String FileName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
        setContentView(R.layout.activity_affirm);
        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
                .detectDiskReads()
                .detectDiskWrites()
                .detectNetwork()
                .penaltyLog()
                .build());
        StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder()
                .detectLeakedSqlLiteObjects()
                .detectLeakedClosableObjects()
                .penaltyLog()
                .penaltyDeath()
                .build());
       //---------------------设置ImageView图片------------------------
        Bundle extras = this.getIntent().getExtras();
        String imageFileUri = extras.getString("MyPicture");
        FileName=imageFileUri;
        setPicToImageView((ImageView) findViewById(R.id.image)
                , new File(imageFileUri));
        //-------------------病害确认提交-----------------------
        findViewById(R.id.disease).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog isExit = new AlertDialog.Builder(affirm.this).create();
                // 设置对话框标题
                isExit.setTitle("System Prompt");
                // 设置对话框消息
                isExit.setMessage("Are you sure to submit?");
                // 添加选择按钮并注册监听
                isExit.setButton("Yes", listener);
                isExit.setButton2("No", listener);
                // 显示对话框
                isExit.show();

            }
            DialogInterface.OnClickListener listener = new DialogInterface.OnClickListener()
            {
                public void onClick(DialogInterface dialog, int which)
                {
                    switch (which)
                    {
                        case AlertDialog.BUTTON_POSITIVE:// "确认"按钮退出程序
                            connect();
                            break;
                        case AlertDialog.BUTTON_NEGATIVE:// "取消"第二个按钮取消对话框
                            break;
                        default:
                            break;
                    }
                }
            };
        });
        //---------------虫害确认提交------------------
        findViewById(R.id.pet).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {


                AlertDialog isExit = new AlertDialog.Builder(affirm.this).create();
                // 设置对话框标题
                isExit.setTitle("System Prompt");
                // 设置对话框消息
                isExit.setMessage("Are you sure to submit?");
                // 添加选择按钮并注册监听
                isExit.setButton("Yes", listener);
                isExit.setButton2("No", listener);
                // 显示对话框
                isExit.show();


            }
            DialogInterface.OnClickListener listener = new DialogInterface.OnClickListener()
            {
                public void onClick(DialogInterface dialog, int which)
                {switch (which)
                {
                    case AlertDialog.BUTTON_POSITIVE:// "确认"按钮退出程序
                        connect();
                        break;
                    case AlertDialog.BUTTON_NEGATIVE:// "取消"第二个按钮取消对话框
                        break;
                    default:
                        break;
                }


                }
            };
        });
        //--------------返回----------------
        findViewById(R.id.back1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(affirm.this, MainActivity.class);
                startActivity(intent);
                affirm.this.finish();
            }
        });

    }
    //-------------------setPicToImageView函数------------------------------
    private void setPicToImageView(ImageView imageView, File imageFile) {
        int imageViewWidth = imageView.getWidth();
        int imageViewHeight = imageView.getHeight();
        BitmapFactory.Options opts = new BitmapFactory.Options();


        opts.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(imageFile.getPath(), opts);

        int bitmapWidth = opts.outWidth;
        int bitmapHeight = opts.outHeight;

        int scale = Math.max(imageViewWidth / bitmapWidth, imageViewHeight / bitmapHeight);

        opts.inSampleSize = scale;

        opts.inPurgeable = true;

        opts.inJustDecodeBounds = false;

        bitmap = BitmapFactory.decodeFile(imageFile.getPath(), opts);
        imageView.setImageBitmap(bitmap);
    }
    //------------------------------通信连接服务器connect函数-----------------------------------
    public void connect()
    {
        try {
            socket = new Socket();
            socket.connect(new InetSocketAddress("114.229.246.19", 8888), 5000);
            socket.setTcpNoDelay(true);
            Toast.makeText(affirm.this, "success", Toast.LENGTH_SHORT).show();
            BufferedOutputStream out=new BufferedOutputStream(socket.getOutputStream());
            BufferedInputStream din = new BufferedInputStream((socket.getInputStream()));
            File img=new File(FileName);
            long a=img.length();
            FileInputStream in = new FileInputStream(img);
            byte[] buffer1=new byte[1024*64];
            int temp=0;
            while((temp=in.read(buffer1))!=-1)
            {
                out.write(buffer1,0,temp);
            }
//            out.flush();

/*            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            //读取图片到ByteArryOutputStream
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
            byte[] bytes=baos.toByteArray();
            int readbuffer=baos.size();
         //   int length=bytes.length;
            out.write(bytes);
            out.flush();*/

            byte[] buffer=new byte[1024*8];
            din.read(buffer);
            String msg=new String(buffer);
            String []mess=msg.split("\\n");
            if(mess.length<3)
            {
                Intent intent=new Intent();
                intent.setClass(affirm.this, correct.class);
                Bundle bundle=new Bundle();
                bundle.putString("MyPicture",FileName);
                bundle.putString("Aname","Sorry,can not discern");
                intent.putExtras(bundle);
                startActivity(intent);
            }
            else {
                Intent intent = new Intent();
                intent.setClass(affirm.this, showInformation.class);
                //声明Bundle对象
                Bundle bundle = new Bundle();
                bundle.putString("data", msg);
                bundle.putString("MyPicture",FileName);
                intent.putExtras(bundle);
                startActivity(intent);
                affirm.this.finish();
            }
//            din.close();
//            out.close();
 //           socket.close();
        }
          catch (UnknownHostException e) {
              Toast.makeText(affirm.this, "fail", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        } catch (IOException e) {
            Toast.makeText(affirm.this, "fail", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }
    //------------------------------系统返回键退出程序-------------------------------------
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
                    affirm.this.finish();
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
