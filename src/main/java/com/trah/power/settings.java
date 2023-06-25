package com.trah.power;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class settings extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        EditText ip_adr = (EditText) findViewById(R.id.config_ip_1);
        EditText mac_adr = (EditText) findViewById(R.id.config_mac_1);
        Button button = (Button) findViewById(R.id.save);
        SharedPreferences sp = getSharedPreferences("ipmac", Context.MODE_PRIVATE);
        String ipa = sp.getString("ip", "");
        String maca = sp.getString("mac", "");
        ip_adr.setText(ipa);
        mac_adr.setText(maca);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ip = ip_adr.getText().toString();
                String mac = mac_adr.getText().toString();
                if (isTrue(mac,ip) == false) {
                        Toast.makeText(getApplicationContext(), "无效ip或者mac地址", Toast.LENGTH_SHORT).show();
                }
                else {
                    SharedPreferences.Editor editor = sp.edit();
                    editor.putString("ip",ip);
                    editor.putString("mac",mac);
                    editor.commit();
                    Toast.makeText(getApplicationContext(), "保存成功", Toast.LENGTH_SHORT).show();
                    finish();
                }

            }
        });
    }
    private boolean isTrue(String mac,String ip) {
        String IPV4="^((\\d|[1-9]\\d|1\\d\\d|2[0-4]\\d|25[0-5]|[*])\\.){3}(\\d|[1-9]\\d|1\\d\\d|2[0-4]\\d|25[0-5]|[*])$";
        String trueMacAddress = "([A-Fa-f0-9]{2}[-.:]{1}){5}[A-Fa-f0-9]{2}";
        if (mac.matches(trueMacAddress) & ip.matches(IPV4)) {
            return true;
        } else {
            return false;

        }
    }


}
