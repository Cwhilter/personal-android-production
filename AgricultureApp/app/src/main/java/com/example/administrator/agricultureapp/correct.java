package com.example.administrator.agricultureapp;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;


public class correct extends Activity {
    Bitmap bitmap=null;
    TextView textView;
    EditText editText;
    Socket socket=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_correct);
        Bundle extras=this.getIntent().getExtras();
        String imageFileUri = extras.getString("MyPicture");
        String title=extras.getString("Aname");
        textView=(TextView)findViewById(R.id.title1);
        textView.setText(title);
        setPicToImageView((ImageView) findViewById(R.id.image2), new File(imageFileUri));
        findViewById(R.id.back4).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                correct.this.finish();
            }
        });
        findViewById(R.id.back3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                correct.this.finish();
            }
        });
        findViewById(R.id.submit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editText=(EditText)findViewById(R.id.edit);
                try {
                    String msg=editText.getText().toString();
                    socket = new Socket();
                    socket.connect(new InetSocketAddress("114.229.246.19", 8889), 5000);
                    BufferedOutputStream out = new BufferedOutputStream(socket.getOutputStream());
                    out.write(msg.getBytes());
                    Toast.makeText(correct.this, "发送成功", Toast.LENGTH_SHORT).show();
                } catch (IOException e1) {
                    Toast.makeText(correct.this, "未链接", Toast.LENGTH_SHORT).show();
                    e1.printStackTrace();
                }
//            din.close();
//            out.close();
                    //           socket.close();

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
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_correct, menu);
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
}
