package com.example.tkw33.homework3modify.GeoJson;

import java.util.ArrayList;
import java.util.LinkedList;

public class LinkLatLng {
    public String link_id;
    public ArrayList<LatLng> coordinate;
    public LinkedList<Float> distance;
    public LinkLatLng(String link_id, ArrayList<LatLng> coordinate, LinkedList<Float> distance){
        this.link_id = link_id;
        this.coordinate = new ArrayList<>();
        this.coordinate = coordinate;
        this.distance = new LinkedList<>();
        this.distance = distance;
    }
}