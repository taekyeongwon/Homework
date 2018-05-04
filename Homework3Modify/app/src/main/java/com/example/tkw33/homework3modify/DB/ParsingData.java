package com.example.tkw33.homework3modify.DB;

import com.example.tkw33.homework3modify.GeoJson.LatLng;

import java.util.ArrayList;

public class ParsingData {
    public int _id;
    public String link_id;
    public double dist = 0.0;
    public double slat = 0.0, slng = 0.0, elat = 0.0, elng = 0.0;
    public ParsingData(int _id, String link_id,
                       double slat, double slng, double elat, double elng, double dist){
        this._id = _id;
        this.link_id = link_id;
        this.slat = slat;
        this.slng = slng;
        this.elat = elat;
        this.elng = elng;
        this.dist = dist;
    }
}