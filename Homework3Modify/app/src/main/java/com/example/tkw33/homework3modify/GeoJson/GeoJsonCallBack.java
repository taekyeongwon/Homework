package com.example.tkw33.homework3modify.GeoJson;

import java.util.ArrayList;

public interface GeoJsonCallBack {
    void onFinish(ArrayList<LinkLatLng> llist);
    void onException();
}
