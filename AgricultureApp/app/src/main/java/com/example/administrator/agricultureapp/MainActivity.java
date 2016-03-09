package com.example.administrator.agricultureapp;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.KeyEvent;

import android.view.View;
import android.widget.Toast;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;



public class MainActivity extends Activity {

    private final int SYSTEM_CAMERA_REQUESTCODE = 1;
    private final int MYAPP_CAMERA_REQUESTCODE = 2;
    private Uri imageFileUri = null;
    private final int TYPE_FILE_IMAGE = 1;
    private final int TYPE_FILE_VIDEO=2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
//                imageFileUri = getOutFileUri(TYPE_FILE_IMAGE);
 //               intent.putExtra(MediaStore.EXTRA_OUTPUT, imageFileUri);
                startActivityForResult(intent, SYSTEM_CAMERA_REQUESTCODE);

            }
        });

        findViewById(R.id.button1).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_PICK);
                intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                //imageFileUri = Media.EXTERNAL_CONTENT_URI;
                startActivityForResult(intent,MYAPP_CAMERA_REQUESTCODE);
            }
        });
        findViewById(R.id.back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent();
                intent.setClass(MainActivity.this, classify.class);
                startActivity(intent);
                MainActivity.this.finish();
            }
        });
        findViewById(R.id.sousuo).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent();
                intent.setClass(MainActivity.this, DBtext.class);
                startActivity(intent);
                MainActivity.this.finish();
            }
        });

    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == SYSTEM_CAMERA_REQUESTCODE && resultCode == RESULT_OK){


            //setContentView(R.layout.confirm);
            Log.i("MyPicture", imageFileUri.getEncodedPath());
            Intent intent = new Intent();
            Bundle bundle = new Bundle();
            bundle.putString("MyPicture", imageFileUri.getEncodedPath());
            intent.putExtras(bundle);
            intent.setClass(MainActivity.this,affirm.class);
            startActivity(intent);
            MainActivity.this.finish();
        }
        if (requestCode == MYAPP_CAMERA_REQUESTCODE && resultCode == RESULT_OK){
            Uri selectedImage = data.getData();
            String[] filePathColumns={MediaStore.Images.Media.DATA};
            Cursor c = this.getContentResolver().query(selectedImage, filePathColumns, null,null, null);
            c.moveToFirst();
            int columnIndex = c.getColumnIndex(filePathColumns[0]);
            String picturePath= c.getString(columnIndex);
            c.close();
            Log.i("MyPicture", picturePath);
            Intent intent = new Intent();
            Bundle bundle = new Bundle();
            bundle.putString("MyPicture", picturePath);
            intent.putExtras(bundle);
            intent.setClass(MainActivity.this,affirm.class);
            startActivity(intent);
            MainActivity.this.finish();

        }

    }
    private Uri getOutFileUri(int fileType) {
        return Uri.fromFile(getOutFile(fileType));
    }

    private File getOutFile(int fileType) {

        String storageState = Environment.getExternalStorageState();
        if (Environment.MEDIA_REMOVED.equals(storageState)){
            Toast.makeText(getApplicationContext(), "SD is not exist", Toast.LENGTH_SHORT).show();
            return null;
        }

        File mediaStorageDir = new File (Environment
                .getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
                ,"MyPictures");
        if (!mediaStorageDir.exists()){
            if (!mediaStorageDir.mkdirs()){
                Log.i("MyPictures", "fail to create store path");
                Log.i("MyPictures", "mediaStorageDir : " + mediaStorageDir.getPath());
                return null;
            }
        }

        File file = new File(getFilePath(mediaStorageDir,fileType));

        return file;
    }
    private String getFilePath(File mediaStorageDir, int fileType){
        String timeStamp =new SimpleDateFormat("yyyyMMdd_HHmmss")
                .format(new Date());
        String filePath = mediaStorageDir.getPath() + File.separator;
        if (fileType == TYPE_FILE_IMAGE){
            filePath += ("IMG_" + timeStamp + ".jpg");
        }else{
            return null;
        }
        return filePath;
    }
    private boolean checkCameraHardWare(Context context){
        PackageManager packageManager = context.getPackageManager();
        if (packageManager.hasSystemFeature(PackageManager.FEATURE_CAMERA)){
            return true;
        }
        return false;
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
                    MainActivity.this.finish();
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

