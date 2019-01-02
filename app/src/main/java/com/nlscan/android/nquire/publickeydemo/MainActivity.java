package com.nlscan.android.nquire.publickeydemo;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.leon.lfilepickerlibrary.LFilePicker;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

/**
  *@date 20190102
  *@author Alan
  *@company nlscan
  *@describe
 **/

public class MainActivity extends AppCompatActivity {
    private static Button btnSetPK;
    private static EditText etPK;
    private static Button btnReadPK;
    private static String SECRET_KEY = "com.secret.key";
    public static final int REQUESTCODE_FROM_ACTIVITY = 1000;
    public static final int ICON_STYLE_BLUE = 1;
    private static  final int READ_EXTERNAL_STORAGE_REQUEST_CODE = 1;
    private static final String DEFAULT_PATH = "/mnt/sdcard";
    private static final String PUB_FILE = "id_rsa.pub";
    private static String TAG = "PKTag";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnSetPK = (Button) findViewById(R.id.btnSetPK);
        etPK = (EditText) findViewById(R.id.etPK);
        btnSetPK.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.putExtra("secretkey", etPK.getText().toString().trim());
                intent.setAction(SECRET_KEY);
                Log.d(TAG,"Before Broadcast.");
                sendBroadcast(intent);
                Log.d(TAG,"After Broadcast.");
                Toast toast = Toast.makeText(getApplicationContext(),"Public Key Set.",Toast.LENGTH_LONG);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();

            }
        });

        btnReadPK = findViewById(R.id.btnReadPK);
        btnReadPK.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                            READ_EXTERNAL_STORAGE_REQUEST_CODE);
                } else {
                    new LFilePicker()
                            .withActivity(MainActivity.this)
                            .withRequestCode(REQUESTCODE_FROM_ACTIVITY)
                            .withTitle((getResources().getString(R.string.msgChooseFile)))
                            .withIconStyle(ICON_STYLE_BLUE)
                            .withMutilyMode(false)
                            .withStartPath(DEFAULT_PATH)//指定初始显示路径
                            .withChooseMode(true)//设置文件夹选择模式,true(默认)为选择文件，false为选择文件夹
                            .withFileFilter(new String[]{"txt", "pub"})
                            .start();
                }
            }
        });

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        doNext(requestCode,grantResults);
    }
    private void doNext(int requestCode, int[] grantResults) {
        if (requestCode == READ_EXTERNAL_STORAGE_REQUEST_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                new LFilePicker()
                        .withActivity(MainActivity.this)
                        .withRequestCode(REQUESTCODE_FROM_ACTIVITY)
                        .withTitle((getResources().getString(R.string.msgChooseFile)))
                        .withIconStyle(ICON_STYLE_BLUE)
                        .withMutilyMode(false)
                        .withStartPath(DEFAULT_PATH)//指定初始显示路径
                        .withChooseMode(true)//设置文件夹选择模式,true(默认)为选择文件，false为选择文件夹
                        .withFileFilter(new String[]{"txt", "pub"})
                        .start();
            } else {
                // Permission Denied
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == REQUESTCODE_FROM_ACTIVITY) {
                String path = data.getStringExtra("path");
                Log.d("PublicKey:",path);
                readPkFile(path);
            }
        }
    }

    private void readPkFile(String filePath){
        File myfile = new File(filePath);
        if (myfile.exists()) {
            try {
                FileInputStream fis = new FileInputStream(myfile);
                InputStreamReader isr = new InputStreamReader(fis, "utf-8");
                char input[] = new char[fis.available()];
                isr.read(input);
                String str = new String(input);
                etPK.setText(str);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

        } else {
            Toast.makeText(getApplicationContext(),"file is not available",Toast.LENGTH_LONG).show();
            etPK.setText("");
        }
    }

}
