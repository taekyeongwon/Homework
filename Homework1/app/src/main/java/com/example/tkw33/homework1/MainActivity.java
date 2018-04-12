package com.example.tkw33.homework1;

import android.app.ListActivity;
import android.content.Context;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.SimpleAdapter;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

public class MainActivity extends ListActivity {
    Date date = new Date(Build.TIME);
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEE MMM dd HH:mm:ss yyyy", Locale.US);
    String dateFormatTime = simpleDateFormat.format(date);

    String[] values =
            {Build.MODEL, Integer.toString(Build.VERSION.SDK_INT), Build.VERSION.SECURITY_PATCH,
                    Build.BOARD, Build.BOOTLOADER, Build.BRAND,
                    Build.DEVICE, Build.DISPLAY, Build.FINGERPRINT, Build.HARDWARE,
                    Build.ID, Build.MANUFACTURER, Build.PRODUCT, Build.TAGS,
                    Build.TYPE, Build.VERSION.CODENAME, Build.VERSION.RELEASE,
                    Build.VERSION.INCREMENTAL, System.getProperty("os.version")+
                    "\n" + Build.USER + "@" + Build.HOST + "\n" + dateFormatTime};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ArrayList<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();
        HashMap<String, String> item;

        String[] sysinfo = getResources().getStringArray(R.array.sysinfo);
        for(int i=0; i<sysinfo.length; i++) {
            item = new HashMap<String, String>();
            item.put("item1", sysinfo[i]);
            item.put("item2", values[i]);
            list.add(item);
        }

        SimpleAdapter simpleAdapter = new SimpleAdapter(this, list,
                android.R.layout.simple_list_item_2, new String[] {"item1","item2"},
                new int[] {android.R.id.text1, android.R.id.text2});

        setListAdapter(simpleAdapter);
    }

}
