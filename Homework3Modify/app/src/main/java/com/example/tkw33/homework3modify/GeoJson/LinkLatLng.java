package com.example.tkw33.homework3modify.GeoJson;

import java.util.ArrayList;

public class LinkLatLng {
    public String link_id;
    public ArrayList<LatLng> coordinate;
    public double distance;
    /*public LinkLatLng(String link_id, ArrayList<LatLng> coordinate){
        //super();
        this.link_id = link_id;
        this.coordinate = new ArrayList<>();
        this.coordinate = coordinate;
    }*/
    public LinkLatLng(String link_id, ArrayList<LatLng> coordinate, double distance){
        this.link_id = link_id;
        this.coordinate = new ArrayList<>();
        this.coordinate = coordinate;
        //this.distance = new ArrayList<>();
        this.distance = distance;
    }
    public static ArrayList<LinkLatLng> llist = new ArrayList<>();
}