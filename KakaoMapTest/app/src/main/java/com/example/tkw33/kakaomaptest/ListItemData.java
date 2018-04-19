package com.example.tkw33.kakaomaptest;

import android.app.Activity;

public class ListItemData {
    public String lat;
    public String lon;
    public String purpose;
    //public Class cls;
    public ListItemData(String lat, String lon, String purpose/*, Class<? extends Activity> cls*/){
        this.lat = lat;
        this.lon = lon;
        this.purpose = purpose;
        //this.cls = cls;
    }

    public static ListItemData[] listItemData = {
            new ListItemData(String.valueOf(36.4844849), String.valueOf(127.7163965), "생활방범"),
            new ListItemData(String.valueOf(38.068901), String.valueOf(128.175262), "교통단속"),
            new ListItemData(String.valueOf(37.490039), String.valueOf(126.732420), "시설물관리")
    };
}
