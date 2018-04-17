package com.example.tkw33.homework2;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
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
    String[] sysinfo;
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ArrayList<ListItemData> arrayList = new ArrayList<>();
        sysinfo = getResources().getStringArray(R.array.sysinfo);

        for(int i = 0; i<sysinfo.length; i++) {
            ListItemData itemData = new ListItemData();
            itemData.infoText = sysinfo[i];
            itemData.valueText = values[i];
            arrayList.add(itemData);
        }

        MyListView myListView = new MyListView(this, arrayList);
        listView = (ListView) findViewById(R.id.listview);
        listView.setAdapter(myListView);

    }

}