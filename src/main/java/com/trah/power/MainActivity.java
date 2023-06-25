package com.trah.power;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        StatusBar statusBar = new StatusBar(MainActivity.this);
        statusBar.hideStatusBar();
        SharedPreferences sp =getSharedPreferences("ipmac",MODE_PRIVATE);
        if (sp.getString("ip","")==""||sp.getString("mac","")==""){
            ToastUtils.showToast(MainActivity.this,"请先设置IP和MAC地址");
            Intent intent = new Intent(this,settings.class);
            startActivity(intent);
        }
        Button button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    String ip=sp.getString("ip","");
                    String mac=sp.getString("mac","");
                    new WakeThread(ip,mac).start();
                    ToastUtils.showToast(MainActivity.this,"执行成功");
                }
                catch (Exception e){
                    ToastUtils.showToast(MainActivity.this,"错误");
                }

            }
        }
        );
        button.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Intent intent = new Intent(MainActivity.this, settings.class);
                startActivity(intent);
                return true;
            }

        });

    }
}

class ToastUtils {
    private static Context context = null;
    private static Toast toast = null;

    public static void showToast(Context context, String text) {
        if (toast == null) {
            toast = Toast.makeText(context, text, Toast.LENGTH_SHORT);
        } else {
            toast.setText(text);
            toast.setDuration(Toast.LENGTH_SHORT);
        }
        toast.show();
    }
}


